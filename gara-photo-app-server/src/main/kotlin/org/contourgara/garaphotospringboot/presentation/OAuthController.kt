package org.contourgara.garaphotospringboot.presentation

import org.contourgara.garaphotospringboot.application.CreateUrlUseCase
import org.contourgara.garaphotospringboot.presentation.response.CreateUrlResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/oauth")
class OAuthController(
  private val createUrlUseCase: CreateUrlUseCase,
) {

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  fun root() {
    // Do nothing
  }

  @GetMapping("url")
  @ResponseStatus(HttpStatus.OK)
  fun createUrl(): CreateUrlResponse {
    return CreateUrlResponse.of(createUrlUseCase.execute())
  }
}
