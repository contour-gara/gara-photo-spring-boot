package org.contourgara.garaphotospringboot.infrastructure

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.TestUtils
import org.contourgara.garaphotospringboot.domain.Media
import org.contourgara.garaphotospringboot.domain.Tweet
import org.contourgara.garaphotospringboot.domain.infrastructure.TwitterClient
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import twitter4j.TwitterException

@ActiveProfiles("test")
@SpringBootTest
@WireMockTest(httpPort = 28080)
class TwitterClientImplTest {
    @Autowired
    lateinit var sut: TwitterClient

    @Test
    fun `ツイートできた場合、ツイート ID が返る`() {
        // setup
        stubFor(
            post(urlEqualTo("/media/upload.json"))
                .willReturn(
                    aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody("{\"media_id\":710511363345354753,\"media_id_string\":\"710511363345354753\",\"size\":11065,\"expires_after_secs\":86400,\"image\":{\"image_type\":\"image/jpeg\",\"w\":800,\"h\":320}}")
                )
        )

        stubFor(
            post(urlEqualTo("/tweets"))
                .withHeader(
                    "Authorization",
                    matching("^OAuth oauth_consumer_key=\"api-key-dummy\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"[^\"]+\",oauth_nonce=\"[^\"]+\",oauth_version=\"1\\.0\",oauth_token=\"oauth1-access-token-dummy\",oauth_signature=\"[^\"]+\"\$")
                )
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalTo("{\"media\":{\"media_ids\":[\"710511363345354753\",\"710511363345354753\",\"710511363345354753\",\"710511363345354753\"]},\"text\":\"tweet\"}"))
                .willReturn(
                    aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody("{\"data\":{\"edit_history_tweet_ids\":[\"1778064547540353187\"],\"id\":\"1778064547540353187\",\"text\":\"tweet\"}}")
                )
        )

        val media = Media(
            listOf(
                TestUtils.getFile("photo/yesterday/20240422/20240422-190001-01.png"),
                TestUtils.getFile("photo/yesterday/20240422/20240422-190002-02.png"),
                TestUtils.getFile("photo/yesterday/20240422/20240422-190003-03.png"),
                TestUtils.getFile("photo/yesterday/20240422/20240422-190004-04.png"),
            )
        )

        // execute
        val actual = sut.tweetWithMedia(Tweet("tweet", media), "accessToken")

        // assert
        val expected = 1778064547540353187
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `ツイートできなかった場合、例外が返る`() {
        // setup
        stubFor(
            post(urlEqualTo("/media/upload.json"))
                .willReturn(
                    aResponse()
                        .withStatus(500)
                )
        )

        stubFor(
            post(urlEqualTo("/tweets"))
                .withHeader(
                    "Authorization",
                    matching("^OAuth oauth_consumer_key=\"api-key-dummy\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"[^\"]+\",oauth_nonce=\"[^\"]+\",oauth_version=\"1\\.0\",oauth_token=\"oauth1-access-token-dummy\",oauth_signature=\"[^\"]+\"\$")
                )
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalTo("{\"media\":{\"media_ids\":[\"710511363345354753\",\"710511363345354753\",\"710511363345354753\",\"710511363345354753\"]},\"text\":\"tweet\"}"))
                .willReturn(
                    aResponse()
                        .withStatus(500)
                )
        )

        val media = Media(
            listOf(
                TestUtils.getFile("photo/yesterday/20240422/20240422-190001-01.png"),
                TestUtils.getFile("photo/yesterday/20240422/20240422-190002-02.png"),
                TestUtils.getFile("photo/yesterday/20240422/20240422-190003-03.png"),
                TestUtils.getFile("photo/yesterday/20240422/20240422-190004-04.png"),
            )
        )

        // execute & assert
        assertThatThrownBy {
            sut.tweetWithMedia(
                Tweet("tweet", media),
                "accessToken"
            )
        }.isInstanceOf(TwitterException::class.java)
    }
}
