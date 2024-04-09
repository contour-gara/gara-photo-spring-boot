package org.contourgara.garaphotospringboot.presentation

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Controller
@RequestMapping("/v1/oauth")
class OAuthController {
  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  fun root() {
    // Do nothing
  }
}
