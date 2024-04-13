package org.contourgara.garaphotospringboot.presentation

import io.restassured.module.mockmvc.RestAssuredMockMvc.*
import org.contourgara.garaphotospringboot.application.CreateUrlUseCase
import org.contourgara.garaphotospringboot.application.dto.CreateUrlDto
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest
class OAuthControllerTest {
  @Autowired
  lateinit var mockMvc: MockMvc

  @MockBean
  lateinit var createUrlUseCase: CreateUrlUseCase

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
  fun `URL 発行エンドポイントに GET した場合、レスポンスコード 200 と URL と code_challenge を取得できる`() {
    // setup
    doReturn(CreateUrlDto("url", "challenge")).`when`(createUrlUseCase).execute()

    // execute & assert
    given()
      .`when`()
      .get("/v1/oauth/url")
      .then()
      .status(HttpStatus.OK)
      .body("url", equalTo("url"))
      .body("codeChallenge", equalTo("challenge"))
  }
}
