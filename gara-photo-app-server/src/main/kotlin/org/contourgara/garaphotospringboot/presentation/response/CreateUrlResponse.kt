package org.contourgara.garaphotospringboot.presentation.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.contourgara.garaphotospringboot.application.dto.CreateUrlDto

data class CreateUrlResponse(
  @JsonProperty("url") val url: String,
  @JsonProperty("codeChallenge") val codeChallenge: String,
) {
  companion object {
    fun of(createUrlDto: CreateUrlDto): CreateUrlResponse {
      return CreateUrlResponse(createUrlDto.url, createUrlDto.codeChallenge)
    }
  }
}
