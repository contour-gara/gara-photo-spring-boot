package org.contourgara.integration

import com.github.database.rider.core.api.connection.ConnectionHolder
import io.restassured.RestAssured.*
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import java.sql.DriverManager

@SpringBootTest
class GaraPhotoSpringBootIT {
  companion object {
    private const val DB_URL: String = "jdbc:postgresql://localhost:5432/sample"
    private const val DB_USER: String = "user"
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
    fun `トークンを取得するためのエンドポイントを GET した場合、URL と検証コードが返る`() {
      // execute & assert
      given()
        .get("/v1/oauth/url")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("url", equalTo("https://twitter.com/i/oauth2/authorize?response_type=code&client_id=client-id-dummy&redirect_uri=http://localhost/dummy&scope=tweet.read%20users.read%20tweet.write%20offline.access&state=state&code_challenge=challenge&code_challenge_method=plain"))
        .body("codeChallenge", equalTo("challenge"))
    }
  }
}
