package org.contourgara.garaphotospringboot.application.usecase

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.application.dto.FindTokenDto
import org.contourgara.garaphotospringboot.application.exception.TokenNotFoundException
import org.contourgara.garaphotospringboot.common.GaraPhotoEnvironment
import org.contourgara.garaphotospringboot.common.TwitterConfig
import org.contourgara.garaphotospringboot.domain.Token
import org.contourgara.garaphotospringboot.domain.infrastructure.TokenProvider
import org.contourgara.garaphotospringboot.domain.infrastructure.TokenRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class FindTokenUseCaseTest {
    @InjectMocks
    lateinit var sut: FindTokenUseCase

    @Mock
    lateinit var twitterConfig: TwitterConfig

    @Mock
    lateinit var tokenProvider: TokenProvider

    @Mock
    lateinit var tokenRepository: TokenRepository

    @Mock
    lateinit var garaPhotoEnvironment: GaraPhotoEnvironment

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `トークンが存在しない場合、例外が投げられる`() {
        // setup
        doReturn("clientId").whenever(twitterConfig).clientId
        doReturn(null).whenever(tokenRepository).find("clientId")

        // execute & assert
        assertThatThrownBy { sut.execute() }.isInstanceOf(TokenNotFoundException::class.java)
    }

    @Test
    fun `トークンが有効期限内の場合、そのままアクセストークンを返す`() {
        // setup
        val token = Token(
            "accessToken",
            "refreshToken",
            "clientId",
            ZonedDateTime.of(LocalDateTime.of(2024, 4, 23, 0, 0, 0), ZoneId.systemDefault())
        )

        doReturn("clientId").whenever(twitterConfig).clientId
        doReturn(token).whenever(tokenRepository).find("clientId")
        doReturn(ZonedDateTime.of(LocalDateTime.of(2024, 4, 23, 1, 0, 0), ZoneId.systemDefault())).whenever(
            garaPhotoEnvironment
        ).getCurrentDateTime()

        // execute
        val actual = sut.execute()

        // assert
        val expected = FindTokenDto("accessToken")
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `トークンが有効期限外の場合、トークンを再取得しアクセストークンを返す`() {
        // setup
        val invalidToken = Token(
            "invalidAccessToken",
            "refreshToken",
            "clientId",
            ZonedDateTime.of(LocalDateTime.of(2024, 4, 23, 0, 0, 0), ZoneId.systemDefault())
        )
        val validToken = Token(
            "validAccessToken",
            "refreshToken",
            "clientId",
            ZonedDateTime.of(LocalDateTime.of(2024, 4, 23, 2, 0, 0), ZoneId.systemDefault())
        )

        doReturn("clientId").whenever(twitterConfig).clientId
        doReturn(invalidToken).whenever(tokenRepository).find("clientId")
        doReturn(ZonedDateTime.of(LocalDateTime.of(2024, 4, 23, 2, 0, 0), ZoneId.systemDefault())).whenever(
            garaPhotoEnvironment
        ).getCurrentDateTime()
        doReturn(validToken).whenever(tokenProvider).fetchTokenByRefreshToken(invalidToken)

        // execute
        val actual = sut.execute()

        // assert
        val expected = FindTokenDto("validAccessToken")
        assertThat(actual).isEqualTo(expected)
        verify(tokenRepository, times(1)).update(validToken)
    }
}
