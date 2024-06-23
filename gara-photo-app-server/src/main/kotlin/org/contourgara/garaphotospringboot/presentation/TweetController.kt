package org.contourgara.garaphotospringboot.presentation

import org.contourgara.garaphotospringboot.application.param.UploadYesterdayParam
import org.contourgara.garaphotospringboot.application.scenario.TweetYesterdayScenario
import org.contourgara.garaphotospringboot.application.usecase.UploadYesterdayUseCase
import org.contourgara.garaphotospringboot.presentation.response.TweetYesterdayResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/tweet")
class TweetController(
  private val tweetYesterdayScenario: TweetYesterdayScenario,
  private val uploadYesterdayUseCase: UploadYesterdayUseCase,
  ) {
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  fun root() {
    // Do nothing
  }

  @PostMapping("yesterday")
  @ResponseStatus(HttpStatus.CREATED)
  fun tweetYesterday(): TweetYesterdayResponse {
    return TweetYesterdayResponse.of(tweetYesterdayScenario.execute())
  }

  @PostMapping("yesterday/upload_media")
  @ResponseStatus(HttpStatus.NO_CONTENT)
    fun uploadYesterday(@RequestPart("photos") files: List<MultipartFile>) {
    uploadYesterdayUseCase.execute(UploadYesterdayParam(
      files.map { it.originalFilename!! },
      files.map { it.bytes }
    ))
  }
}
