package org.contourgara.garaphotospringboot.application.usecase

import org.contourgara.garaphotospringboot.application.param.FetchTokenParam
import org.contourgara.garaphotospringboot.common.TwitterConfig
import org.contourgara.garaphotospringboot.domain.Authorization
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

class FetchTokenUseCaseTest {
    @InjectMocks
    lateinit var sut: FetchTokenUseCase

    @Mock
    lateinit var twitterConfig: TwitterConfig

    @Mock
    lateinit var tokenProvider: TokenProvider

    @Mock
    lateinit var tokenRepository: TokenRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `トークンを取得し、リポジトリに保存できる`() {
        // setup
        val token = Token(
            "accessToken",
            "refreshToken",
            "clientId",
            LocalDateTime.of(2024, 4, 14, 4, 10, 30)
        )

        doReturn("clientId").whenever(twitterConfig).clientId
        doReturn("redirectUri").whenever(twitterConfig).redirectUri
        doReturn(token).whenever(tokenProvider)
            .fetchToken(Authorization("clientId", "redirectUri", "code", "challenge"))

        val fetchTokenParam = FetchTokenParam("code", "challenge")

        // execute
        sut.execute(fetchTokenParam)

        // assert
        verify(tokenRepository, times(1)).insert(token)
    }
}
