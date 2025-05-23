package org.contourgara.garaphotospringboot.presentation

import io.kotest.core.spec.style.WordSpec
import io.restassured.module.mockmvc.RestAssuredMockMvc.*
import io.restassured.module.mockmvc.kotlin.extensions.*
import org.contourgara.garaphotospringboot.TestUtils.getFile
import org.contourgara.garaphotospringboot.application.dto.TweetYesterdayDto
import org.contourgara.garaphotospringboot.application.param.UploadYesterdayParam
import org.contourgara.garaphotospringboot.application.scenario.TweetYesterdayScenario
import org.contourgara.garaphotospringboot.application.usecase.UploadYesterdayUseCase
import org.hamcrest.Matchers.*
import org.mockito.kotlin.*
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest(controllers = [TweetController::class])
class TweetControllerTest(
    private val mockMvc: MockMvc,
    @MockitoBean private val tweetYesterdayScenario: TweetYesterdayScenario,
    @MockitoBean private val uploadYesterdayUseCase: UploadYesterdayUseCase,
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

    "yesterday ツイートエンドポイントに POST した場合" should {
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
            val photo1 = getFile("photo/yesterday/20240422/20240422-190001-01.png")
            val photo2 = getFile("photo/yesterday/20240422/20240422-190002-02.png")

            // execute & assert
            Given {
                multiPart("photos", photo1)
                multiPart("photos", photo2)
            } When {
                post("/v1/tweet/yesterday/upload_media")
            } Then {
                status(HttpStatus.NO_CONTENT)
            }

            verify(uploadYesterdayUseCase, times(1))
                .execute(any<UploadYesterdayParam>())
        }
    }
})
