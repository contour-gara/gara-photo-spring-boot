package org.contourgara.garaphotospringboot.presentation.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.contourgara.garaphotospringboot.application.dto.FindTokenDto

data class FindTokenResponse(
  @JsonProperty("accessToken")
  val accessToken: String
) {
  companion object {
    fun of(findTokenDto: FindTokenDto): FindTokenResponse {
      return FindTokenResponse(findTokenDto.accessToken)
    }
  }
}
