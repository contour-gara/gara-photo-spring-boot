package org.contourgara.garaphotospringboot.infrastructure

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.common.ResourceUtils
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
    stubFor(post(urlEqualTo("/media/upload.json"))
      .willReturn(aResponse()
        .withStatus(201)
        .withHeader("Content-Type", "application/json; charset=utf-8")
        .withBody("{\"media_id\":710511363345354753,\"media_id_string\":\"710511363345354753\",\"size\":11065,\"expires_after_secs\":86400,\"image\":{\"image_type\":\"image/jpeg\",\"w\":800,\"h\":320}}")))

    stubFor(post(urlEqualTo("/tweets"))
      .withHeader("Authorization", containing("OAuth"))
      .withHeader("Authorization", containing("oauth_consumer_key=\"api-key-dummy\""))
      .withHeader("Authorization", containing("oauth_signature_method=\"HMAC-SHA1\""))
      .withHeader("Authorization", containing("oauth_version=\"1.0\""))
      .withHeader("Authorization", containing("oauth_token=\"oauth1-access-token-dummy\""))
      .withHeader("Content-Type", equalTo("application/json"))
      .withRequestBody(equalTo("{\"media\":{\"media_ids\":[\"710511363345354753\",\"710511363345354753\",\"710511363345354753\",\"710511363345354753\"]},\"text\":\"tweet\"}"))
      .willReturn(aResponse()
        .withStatus(201)
        .withHeader("Content-Type", "application/json; charset=utf-8")
        .withBody("{\"data\":{\"edit_history_tweet_ids\":[\"1778064547540353187\"],\"id\":\"1778064547540353187\",\"text\":\"tweet\"}}")))

    val media = Media(listOf(
      ResourceUtils.getFile("photo/20240422/1.jpg"),
      ResourceUtils.getFile("photo/20240422/2.jpg"),
      ResourceUtils.getFile("photo/20240422/3.jpg"),
      ResourceUtils.getFile("photo/20240422/4.jpg"),
    ))

    // execute
    val actual = sut.tweetWithMedia(Tweet("tweet", media), "accessToken")

    // assert
    val expected = 1778064547540353187
    assertThat(actual).isEqualTo(expected)
  }

  @Test
  fun `ツイートできなかった場合、ツイート ID が返る`() {
    // setup
    stubFor(post(urlEqualTo("/media/upload.json"))
      .willReturn(aResponse()
        .withStatus(500)))

    stubFor(post(urlEqualTo("/tweets"))
      .withHeader("Authorization", containing("OAuth"))
      .withHeader("Authorization", containing("oauth_consumer_key=\"api-key-dummy\""))
      .withHeader("Authorization", containing("oauth_signature_method=\"HMAC-SHA1\""))
      .withHeader("Authorization", containing("oauth_version=\"1.0\""))
      .withHeader("Authorization", containing("oauth_token=\"oauth1-access-token-dummy\""))
      .withHeader("Content-Type", equalTo("application/json"))
      .withRequestBody(equalTo("{\"media\":{\"media_ids\":[\"710511363345354753\",\"710511363345354753\",\"710511363345354753\",\"710511363345354753\"]},\"text\":\"tweet\"}"))
      .willReturn(aResponse()
        .withStatus(500)))

    val media = Media(listOf(
      ResourceUtils.getFile("photo/20240422/1.jpg"),
      ResourceUtils.getFile("photo/20240422/2.jpg"),
      ResourceUtils.getFile("photo/20240422/3.jpg"),
      ResourceUtils.getFile("photo/20240422/4.jpg"),
    ))

    // execute & assert
    assertThatThrownBy { sut.tweetWithMedia(Tweet("tweet", media), "accessToken") }.isInstanceOf(TwitterException::class.java)
  }
}
