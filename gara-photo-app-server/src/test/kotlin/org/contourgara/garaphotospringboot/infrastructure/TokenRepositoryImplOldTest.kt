package org.contourgara.garaphotospringboot.infrastructure

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.domain.Token
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class TokenRepositoryImplOldTest {
    @InjectMocks
    lateinit var sut: TokenRepositoryImplOld

    @Mock
    lateinit var tokenMapper: TokenMapper

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `トークンを保存するためにマッパーを呼び出す`() {
        // setup
        val token = Token(
            "accessToken",
            "refreshToken",
            "clientId",
            ZonedDateTime.of(LocalDateTime.of(2024, 4, 14, 5, 13, 0), ZoneId.systemDefault())
        )

        // execute
        sut.insert(token)

        // assert
        verify(tokenMapper, times(1)).insert(any())
    }

    @Test
    fun `トークンが取得できる`() {
        // setup
        val tokenEntity = TokenEntity("accessToken", "refreshToken", "2024-04-14T05:13:00.000+09:00[Asia/Tokyo]")

        doReturn(tokenEntity).whenever(tokenMapper).find()

        // execute
        val actual = sut.find("clientId")

        // assert
        val expected = Token(
            "accessToken",
            "refreshToken",
            "clientId",
            ZonedDateTime.of(LocalDateTime.of(2024, 4, 14, 5, 13, 0), ZoneId.systemDefault())
        )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `トークン取得でトークンがなかった場合、null が返る`() {
        // setup
        doReturn(null).whenever(tokenMapper).find()

        // execute
        val actual = sut.find("clientId")

        // assert
        assertThat(actual).isNull()
    }

    @Test
    fun `トークンを更新するためにマッパーを呼び出す`() {
        // setup
        val token = Token(
            "accessToken2",
            "refreshToken2",
            "clientId",
            ZonedDateTime.of(LocalDateTime.of(2024, 4, 14, 5, 13, 0), ZoneId.systemDefault())
        )

        // execute
        sut.update(token)

        // assert
        verify(tokenMapper, times(1)).update(any())
    }
}
