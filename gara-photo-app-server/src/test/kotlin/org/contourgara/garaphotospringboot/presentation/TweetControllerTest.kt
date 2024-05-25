package org.contourgara.garaphotospringboot.presentation

import io.restassured.module.mockmvc.RestAssuredMockMvc.*
import org.contourgara.garaphotospringboot.application.dto.TweetYesterdayDto
import org.contourgara.garaphotospringboot.application.scenario.TweetYesterdayScenario
import org.contourgara.garaphotospringboot.application.usecase.CreateUrlUseCase
import org.contourgara.garaphotospringboot.application.usecase.FetchTokenUseCase
import org.contourgara.garaphotospringboot.application.usecase.FindTokenUseCase
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest(controllers = [TweetController::class])
class TweetControllerTest {
  @Autowired
  lateinit var mockMvc: MockMvc

  @MockBean
  lateinit var tweetYesterdayScenario: TweetYesterdayScenario

  @BeforeEach
  fun setUp() {
    mockMvc(mockMvc)
  }

  @Test
  fun `ルートにアクセスした場合、レスポンスコード 200 が返る`() {
    // execute & assert
    given()
      .`when`()
      .get("/v1/tweet")
      .then()
      .status(HttpStatus.OK)
  }

  @Test
  fun `yesterday ツイートエンドポイントに POST した場合、レスポンスコード 201 が返り、ツイート ID を取得できる`() {
    // setup
    doReturn(TweetYesterdayDto("1")).whenever(tweetYesterdayScenario).execute()

    // execute & assert
    given()
      .`when`()
      .post("/v1/tweet/yesterday")
      .then()
      .status(HttpStatus.CREATED)
      .body("tweetId", equalTo("1"))
  }
}
