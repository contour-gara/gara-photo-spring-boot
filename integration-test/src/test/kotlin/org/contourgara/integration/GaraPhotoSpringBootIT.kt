package org.contourgara.integration

import com.github.database.rider.core.api.configuration.DBUnit
import com.github.database.rider.core.api.configuration.Orthography
import com.github.database.rider.core.api.connection.ConnectionHolder
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.core.api.dataset.ExpectedDataSet
import com.github.database.rider.junit5.api.DBRider
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import io.restassured.RestAssured.*
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.sql.DriverManager

@SpringBootTest
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@WireMockTest(httpPort = 28080)
class GaraPhotoSpringBootIT {
  companion object {
    private const val DB_URL: String = "jdbc:postgresql://localhost:5432/gara_photo"
    private const val DB_USER: String = "gara"
    private const val DB_PASSWORD: String = "password"

    private val connectionHolder: ConnectionHolder = ConnectionHolder {
      DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
    }

    @BeforeAll
    @JvmStatic
    fun setUpAll() {
      baseURI = "http://localhost:8080"
    }
  }

  @Nested
  inner class `OAuth エンドポイント` {
    @Test
    @DataSet(value = ["datasets/setup/0-token.yml"])
    @ExpectedDataSet(value = ["datasets/expected/0-token.yml"])
    fun `認可エンドポイントを取得するためのエンドポイントを GET した場合、レスポンスコード 200 と URL と検証コードが返る`() {
      // execute & assert
      given()
        .get("/v1/oauth/url")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("url", Matchers.equalTo("https://twitter.com/i/oauth2/authorize?response_type=code&client_id=client-id-dummy&redirect_uri=http://localhost/dummy&scope=tweet.read%20users.read%20tweet.write%20offline.access&state=state&code_challenge=challenge&code_challenge_method=plain"))
        .body("codeChallenge", Matchers.equalTo("challenge"))
    }

    @Test
    @DataSet(value = ["datasets/setup/0-token.yml"])
    @ExpectedDataSet(value = ["datasets/expected/1-token.yml"])
    fun `アクセストークンを取得するためのエンドポイントを POST した場合、レスポンスコード 201 が返る`() {
      // setup
      stubFor(post(urlEqualTo("/oauth2/token"))
        .withHeader("Content-Type", equalTo("application/x-www-form-urlencoded"))
        .withHeader("Authorization", equalTo("Basic Y2xpZW50LWlkLWR1bW15OmNsaWVudC1zZWNyZXQtZHVtbXk="))
        .withRequestBody(equalTo("code=fugafuga&grant_type=authorization_code&client_id=client-id-dummy&redirect_uri=http%3A%2F%2Flocalhost%2Fdummy&code_verifier=hogehoge"))
        .willReturn(aResponse()
          .withStatus(200)
          .withHeader("Content-Type", "application/json; charset=utf-8")
          .withBody("{\"token_type\":\"bearer\",\"expires_in\":7200,\"access_token\":\"access-token\",\"scope\":\"tweet.write users.read tweet.read offline.access\",\"refresh_token\":\"refresh-token\"}")))

      // execute & assert
      given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body("{\"code\": \"fugafuga\",\"codeChallenge\": \"hogehoge\"}")
        .post("/v1/oauth/token")
        .then()
        .statusCode(HttpStatus.NO_CONTENT.value())
    }
  }
}
