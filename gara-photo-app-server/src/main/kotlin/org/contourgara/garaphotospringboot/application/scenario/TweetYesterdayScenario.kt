package org.contourgara.garaphotospringboot.application.scenario

import org.contourgara.garaphotospringboot.application.dto.TweetYesterdayDto
import org.springframework.stereotype.Service

@Service
class TweetYesterdayScenario {
  fun execute(): TweetYesterdayDto {
    return TweetYesterdayDto("1")
  }
}
