package org.contourgara.garaphotospringboot.application.scenario

import org.contourgara.garaphotospringboot.application.dto.TweetYesterdayDto
import org.contourgara.garaphotospringboot.application.usecase.FindTokenUseCase
import org.contourgara.garaphotospringboot.application.usecase.TweetYesterdayUseCase
import org.springframework.stereotype.Service

@Service
class TweetYesterdayScenario(
  private val findTokenUseCase: FindTokenUseCase,
  private val tweetYesterdayUseCase: TweetYesterdayUseCase,
  ) {
  fun execute(): TweetYesterdayDto {
    val findTokenDto = findTokenUseCase.execute()
    return tweetYesterdayUseCase.execute(findTokenDto.accessToken)
  }
}
