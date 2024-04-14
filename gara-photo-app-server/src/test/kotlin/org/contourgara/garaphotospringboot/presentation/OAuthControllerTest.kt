package org.contourgara.garaphotospringboot.presentation

import io.restassured.module.mockmvc.RestAssuredMockMvc.*
import org.contourgara.garaphotospringboot.application.CreateUrlUseCase
import org.contourgara.garaphotospringboot.application.FetchTokenUseCase
import org.contourgara.garaphotospringboot.application.FindTokenUseCase
import org.contourgara.garaphotospringboot.application.dto.CreateUrlDto
import org.contourgara.garaphotospringboot.application.dto.FindTokenDto
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest
class OAuthControllerTest {
  @Autowired
  lateinit var mockMvc: MockMvc

  @MockBean
  lateinit var createUrlUseCase: CreateUrlUseCase
  @MockBean
  lateinit var fetchTokenUseCase: FetchTokenUseCase
  @MockBean
  lateinit var findTokenUseCase: FindTokenUseCase

  @BeforeEach
  fun setUp() {
    mockMvc(mockMvc)
  }

  @Test
  fun `ルートにアクセスした場合、レスポンスコード 200 が返る`() {
    // execute & assert
    given()
      .`when`()
      .get("/v1/oauth")
      .then()
      .status(HttpStatus.OK)
  }

  @Test
  fun `URL 発行エンドポイントに GET した場合、レスポンスコード 200 が返り、と URL と code_challenge を取得できる`() {
    // setup
    doReturn(CreateUrlDto("url", "challenge")).whenever(createUrlUseCase).execute()

    // execute & assert
    given()
      .`when`()
      .get("/v1/oauth/url")
      .then()
      .status(HttpStatus.OK)
      .body("url", equalTo("url"))
      .body("codeChallenge", equalTo("challenge"))
  }

  @Test
  fun `トークン取得エンドポイントに POST した場合、レスポンスコード 204 が返る`() {
    // execute & assert
    given()
      .contentType(MediaType.APPLICATION_JSON)
      .body("{\"code\": \"dummy\",\"codeChallenge\": \"dummy\"}")
      .`when`()
      .post("/v1/oauth/token")
      .then()
      .status(HttpStatus.NO_CONTENT)

    verify(fetchTokenUseCase, times(1)).execute(any())
  }

  @Test
  fun `トークン取得エンドポイントに GET した場合、レスポンスコード 200 が返り、とアクセストークンを取得できる`() {
    // setup
    doReturn(FindTokenDto("accessToken")).whenever(findTokenUseCase).execute()

    // execute & assert
    given()
      .`when`()
      .get("/v1/oauth/token")
      .then()
      .status(HttpStatus.OK)
      .body("accessToken", equalTo("accessToken"))
  }
}
