package org.contourgara.garaphotospringboot.presentation

import io.kotest.core.spec.style.WordSpec
import io.restassured.module.mockmvc.RestAssuredMockMvc.*
import io.restassured.module.mockmvc.kotlin.extensions.Given
import io.restassured.module.mockmvc.kotlin.extensions.Then
import io.restassured.module.mockmvc.kotlin.extensions.When
import org.contourgara.garaphotospringboot.application.dto.TweetYesterdayDto
import org.contourgara.garaphotospringboot.application.scenario.TweetYesterdayScenario
import org.contourgara.garaphotospringboot.application.usecase.UploadYesterdayUseCase
import org.contourgara.garaphotospringboot.common.ResourceUtils.getFile
import org.hamcrest.Matchers.*
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest(controllers = [TweetController::class])
class TweetControllerTest(
  private val mockMvc: MockMvc,
  @MockBean private val tweetYesterdayScenario: TweetYesterdayScenario,
  @MockBean private val uploadYesterdayUseCase: UploadYesterdayUseCase,
) : WordSpec({
  beforeEach {
    mockMvc(mockMvc)
  }

  "ルートにアクセスした場合" should {
    "レスポンスコード 200 が返る" {
      // execute & assert
      Given {
        body("")
      } When {
        get("/v1/tweet")
      } Then {
        status(HttpStatus.OK)
      }
    }
  }

  "yesterday ツイートエンドポイントに POST した場合" should  {
    "レスポンスコード 201 が返り、ツイート ID を取得できる" {
      // setup
      doReturn(TweetYesterdayDto("1")).whenever(tweetYesterdayScenario).execute()

      // execute & assert
      Given {
        body("")
      } When {
        post("/v1/tweet/yesterday")
      } Then {
        status(HttpStatus.CREATED)
        body("tweetId", equalTo("1"))
      }
    }
  }

  "yesterday アップロードエンドポイントに POST した場合" should {
    "レスポンスコード 204 が返る" {
      // setup
      val photo = getFile("photo/yesterday/20240619-100940-L1003318-LEICA M10 MONOCHROM.jpg")

      // execute & assert
      Given {
        multiPart("photos", listOf(photo))
      } When {
        post("/v1/tweet/yesterday/upload_media")
      } Then {
        status(HttpStatus.NO_CONTENT)
      }
    }
  }
})
