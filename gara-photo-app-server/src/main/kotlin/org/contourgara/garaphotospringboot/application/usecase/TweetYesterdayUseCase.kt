package org.contourgara.garaphotospringboot.application.usecase

import org.contourgara.garaphotospringboot.application.dto.TweetYesterdayDto
import org.springframework.stereotype.Service

@Service
class TweetYesterdayUseCase {
  fun execute(accessToken: String): TweetYesterdayDto {
    return TweetYesterdayDto("1")
  }
}
