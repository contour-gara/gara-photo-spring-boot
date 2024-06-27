package org.contourgara.integration

import com.github.database.rider.core.api.configuration.DBUnit
import com.github.database.rider.core.api.configuration.Orthography
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.core.api.dataset.ExpectedDataSet
import com.github.database.rider.junit5.api.DBRider
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import io.restassured.RestAssured.*
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers
import org.contourgara.integration.TestUtils.getFile
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

@SpringBootTest
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@WireMockTest(httpPort = 28080)
class GaraPhotoSpringBootIT {
  companion object {
    @BeforeAll
    @JvmStatic
    fun setUpAll() {
      baseURI = "http://localhost:8080"
    }
  }

  @Test
  @DataSet(value = ["datasets/setup/0-token.yml"])
  @ExpectedDataSet(value = ["datasets/expected/0-token.yml"])
  fun `認可エンドポイントを取得するためのエンドポイントを GET した場合、レスポンスコード 200 と URL と検証コードが返る`() {
    // execute & assert
    Given {
      body("")
    } When {
      get("/v1/oauth/url")
    } Then {
      statusCode(HttpStatus.OK.value())
      body("url", Matchers.equalTo("""
        https://twitter.com/i/oauth2/authorize?
        response_type=code&
        client_id=client-id-dummy&
        redirect_uri=http://localhost/dummy&
        scope=tweet.read%20users.read%20tweet.write%20offline.access&state=state&
        code_challenge=challenge&
        code_challenge_method=plain
      """.trimIndent().replace(System.lineSeparator(), "")
      ))
      body("codeChallenge", Matchers.equalTo("challenge"))
    }
  }

  @Test
  @DataSet(value = ["datasets/setup/0-token.yml"])
  @ExpectedDataSet(value = ["datasets/expected/1-token.yml"])
  fun `アクセストークンを取得するためのエンドポイントを POST した場合、レスポンスコード 201 が返る`() {
    // setup
    stubFor(
      post(urlEqualTo("/oauth2/token")).withHeader("Content-Type", equalTo("application/x-www-form-urlencoded"))
        .withHeader("Authorization", equalTo("Basic Y2xpZW50LWlkLWR1bW15OmNsaWVudC1zZWNyZXQtZHVtbXk="))
        .withRequestBody(equalTo("""
          code=fugafuga&
          grant_type=authorization_code&
          client_id=client-id-dummy&
          redirect_uri=http%3A%2F%2Flocalhost%2Fdummy&
          code_verifier=hogehoge
        """.trimIndent().replace(System.lineSeparator(), "")
        ))
        .willReturn(
          aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json; charset=utf-8")
            .withBody("""
              {
                "token_type":"bearer",
                "expires_in":7200,
                "access_token":"access-token",
                "scope":"tweet.write users.read tweet.read offline.access",
                "refresh_token":"refresh-token"
              }
            """.trimIndent()
            )
        )
    )

    // execute & assert
    Given {
      contentType(MediaType.APPLICATION_JSON_VALUE)
      body("""{"code": "fugafuga","codeChallenge": "hogehoge"}""")
    } When {
      post("/v1/oauth/token")
    } Then {
      statusCode(HttpStatus.NO_CONTENT.value())
    }
  }

  @Nested
  inner class `アクセストークンを取得するためのエンドポイントを GET した場合` {
    @Test
    @DataSet(value = ["datasets/setup/1-token.yml"])
    @ExpectedDataSet(value = ["datasets/expected/1-token.yml"])
    fun `レスポンスコード 200 とアクセストークンが返る`() {
      // execute & assert
      Given {
        body("")
      } When {
        get("/v1/oauth/token")
      } Then {
        statusCode(HttpStatus.OK.value())
        body("accessToken", Matchers.equalTo("access-token"))
      }
    }

    @Test
    @DataSet(value = ["datasets/setup/1-token-old.yml"])
    @ExpectedDataSet(value = ["datasets/expected/1-token-update.yml"])
    fun `かつトークン取得が古い場合、レスポンスコード 200 と新しいアクセストークンが返る`() {
      // setup
      stubFor(
        post(urlEqualTo("/oauth2/token")).withHeader("Content-Type", equalTo("application/x-www-form-urlencoded"))
          .withHeader("Authorization", equalTo("Basic Y2xpZW50LWlkLWR1bW15OmNsaWVudC1zZWNyZXQtZHVtbXk="))
          .withRequestBody(equalTo("grant_type=refresh_token&refresh_token=refresh-token&client_id=client-id-dummy"))
          .willReturn(
            aResponse()
              .withStatus(200)
              .withHeader("Content-Type", "application/json; charset=utf-8")
              .withBody("""
                {
                  "token_type":"bearer",
                  "expires_in":7200,
                  "access_token":"access-token2",
                  "scope":"tweet.write users.read tweet.read offline.access",
                  "refresh_token":"refresh-token2"
                }
              """.trimIndent()
              )
          )
      )

      // execute & assert
      given().get("/v1/oauth/token").then().statusCode(HttpStatus.OK.value())
        .body("accessToken", Matchers.equalTo("access-token2"))
      Given {
        body("")
      } When {
        get("/v1/oauth/token")
      } Then {
        statusCode(HttpStatus.OK.value())
        body("accessToken", Matchers.equalTo("access-token2"))
      }
    }
  }


  @Test
  @DataSet(value = ["datasets/setup/1-token.yml"])
  @ExpectedDataSet(value = ["datasets/expected/1-token.yml"])
  fun `yesterday シリーズを投稿できる`() {
    // setup
    stubFor(
      post(urlEqualTo("/media/upload.json"))
        .willReturn(
          aResponse().withStatus(201).withHeader("Content-Type", "application/json; charset=utf-8")
            .withBody("""
              {
                "media_id":710511363345354753,
                "media_id_string":"710511363345354753",
                "size":11065,
                "expires_after_secs":86400,
                "image":{"image_type":"image/jpeg","w":800,"h":320}
              }
            """.trimIndent())
        )
    )

    stubFor(
      post(urlEqualTo("/tweets"))
        .withHeader("Authorization", matching("""
          OAuth oauth_consumer_key="api-key-dummy",
          oauth_signature_method="HMAC-SHA1",
          oauth_timestamp="[^"]+",
          oauth_nonce="[^"]+",
          oauth_version="1\.0",
          oauth_token="oauth1-access-token-dummy",
          oauth_signature="[^"]+"${'$'}
        """.trimIndent().replace(System.lineSeparator(), "")))
        .withHeader("Content-Type", equalTo("application/json"))
        .withRequestBody(equalTo("""
          {
          "media":{"media_ids":["710511363345354753","710511363345354753","710511363345354753","710511363345354753"]},
          "text":"yesterday"
          }
          """.trimIndent().replace(System.lineSeparator(), "")))
        .willReturn(
          aResponse()
            .withStatus(201)
            .withHeader("Content-Type", "application/json; charset=utf-8")
            .withBody("""
              {"data":{"edit_history_tweet_ids":["1778064547540353187"],"id":"1778064547540353187","text":"tweet"}}
            """.trimIndent())
        )
    )

    // execute & assert
    Given {
      body("")
    } When {
      post("/v1/tweet/yesterday")
    } Then {
      statusCode(HttpStatus.CREATED.value())
      body("tweetId", Matchers.equalTo("1778064547540353187"))
    }
  }

  @Test
  @DataSet(value = ["datasets/setup/0-token.yml"])
  @ExpectedDataSet(value = ["datasets/expected/0-token.yml"])
  fun `写真をアップロードできる`() {
    // setup
    val photo1 = getFile("photo/yesterday/20240620/20240620-190001-01.png")
    val photo2 = getFile("photo/yesterday/20240620/20240620-190002-02.png")

    // execute & assert
    Given {
      multiPart("photos", photo1)
      multiPart("photos", photo2)
    } When {
      post("/v1/tweet/yesterday/upload_media")
    } Then {
      statusCode(HttpStatus.NO_CONTENT.value())
    }
  }
}
