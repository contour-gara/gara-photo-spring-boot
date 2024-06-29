package org.contourgara.garaphotospringboot.infrastructure

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.domain.Authorization
import org.contourgara.garaphotospringboot.domain.AuthorizationSetting
import org.contourgara.garaphotospringboot.domain.Token
import org.contourgara.garaphotospringboot.domain.infrastructure.TokenProvider
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import twitter4j.TwitterException
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

@ActiveProfiles("test")
@SpringBootTest
@WireMockTest(httpPort = 28080)
class TokenProviderImplTest {
    @Autowired
    lateinit var sut: TokenProvider

    @Test
    fun `認可 URL が生成できる`() {
        // execute
        val actual = sut.createUrl(
            AuthorizationSetting(
                "client-id-dummy",
                "http://localhost/dummy",
                listOf("dummy", "dummy"),
                "challenge"
            )
        )

        // assert
        val expected =
            "https://twitter.com/i/oauth2/authorize?response_type=code&client_id=client-id-dummy&redirect_uri=http://localhost/dummy&scope=dummy%20dummy&state=state&code_challenge=challenge&code_challenge_method=plain"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `アクセストークンを取得できる場合、トークンを取得できる`() {
        // setup
        stubFor(
            post(urlEqualTo("/oauth2/token"))
                .withHeader("Content-Type", equalTo("application/x-www-form-urlencoded"))
                .withHeader("Authorization", equalTo("Basic Y2xpZW50LWlkLWR1bW15OmNsaWVudC1zZWNyZXQtZHVtbXk="))
                .withRequestBody(equalTo("code=code&grant_type=authorization_code&client_id=client-id&redirect_uri=redirect-uri&code_verifier=code-challenge"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody("{\"token_type\":\"bearer\",\"expires_in\":7200,\"access_token\":\"access-token\",\"scope\":\"tweet.write users.read tweet.read offline.access\",\"refresh_token\":\"refresh-token\"}")
                )
        )

        // execute
        val actual = sut.fetchToken(Authorization("client-id", "redirect-uri", "code", "code-challenge"))

        // assert
        val expected = Token(
            "access-token",
            "refresh-token",
            "client-id",
            ZonedDateTime.of(LocalDateTime.of(2024, 4, 23, 0, 0, 0), ZoneId.systemDefault())
        )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `アクセストークンが取得できない場合、例外が投げられる`() {
        // setup
        stubFor(
            post(urlEqualTo("/oauth2/token"))
                .withHeader("Content-Type", equalTo("application/x-www-form-urlencoded"))
                .withHeader("Authorization", equalTo("Basic Y2xpZW50LWlkLWR1bW15OmNsaWVudC1zZWNyZXQtZHVtbXk="))
                .withRequestBody(equalTo("code=code&grant_type=authorization_code&client_id=client-id&redirect_uri=redirect-uri&code_verifier=code-challenge"))
                .willReturn(
                    aResponse()
                        .withStatus(500)
                )
        )

        // execute & assert
        assertThatThrownBy {
            sut.fetchToken(
                Authorization(
                    "client-id",
                    "redirect-uri",
                    "code",
                    "code-challenge"
                )
            )
        }.isInstanceOf(TwitterException::class.java)
    }

    @Test
    fun `リフレッショントークンからアクセストークンを取得できる場合、トークンを取得できる`() {
        // setup
        stubFor(
            post(urlEqualTo("/oauth2/token"))
                .withHeader("Content-Type", equalTo("application/x-www-form-urlencoded"))
                .withHeader("Authorization", equalTo("Basic Y2xpZW50LWlkLWR1bW15OmNsaWVudC1zZWNyZXQtZHVtbXk="))
                .withRequestBody(equalTo("grant_type=refresh_token&refresh_token=refresh-token&client_id=client-id"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody("{\"token_type\":\"bearer\",\"expires_in\":7200,\"access_token\":\"access-token2\",\"scope\":\"tweet.write users.read tweet.read offline.access\",\"refresh_token\":\"refresh-token2\"}")
                )
        )

        // execute
        val actual = sut.fetchTokenByRefreshToken(
            Token(
                "access-token",
                "refresh-token",
                "client-id",
                ZonedDateTime.of(LocalDateTime.of(2024, 4, 20, 0, 0, 0), ZoneId.systemDefault())
            )
        )

        // assert
        val expected = Token(
            "access-token2",
            "refresh-token2",
            "client-id",
            ZonedDateTime.of(LocalDateTime.of(2024, 4, 23, 0, 0, 0), ZoneId.systemDefault())
        )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `リフレッショントークンからアクセストークンを取得できない場合、例外が投げられる`() {
        // setup
        stubFor(
            post(urlEqualTo("/oauth2/token"))
                .withHeader("Content-Type", equalTo("application/x-www-form-urlencoded"))
                .withHeader("Authorization", equalTo("Basic Y2xpZW50LWlkLWR1bW15OmNsaWVudC1zZWNyZXQtZHVtbXk="))
                .withRequestBody(equalTo("grant_type=refresh_token&refresh_token=refresh-token&client_id=client-id"))
                .willReturn(
                    aResponse()
                        .withStatus(500)
                )
        )

        // execute & assert
        assertThatThrownBy {
            sut.fetchTokenByRefreshToken(
                Token(
                    "access-token",
                    "refresh-token",
                    "client-id",
                    ZonedDateTime.of(LocalDateTime.of(2024, 4, 20, 0, 0, 0), ZoneId.systemDefault())
                )
            )
        }.isInstanceOf(TwitterException::class.java)
    }
}
