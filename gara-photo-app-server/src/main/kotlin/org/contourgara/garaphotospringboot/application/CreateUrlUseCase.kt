package org.contourgara.garaphotospringboot.application

import org.contourgara.garaphotospringboot.application.dto.CreateUrlDto
import org.springframework.stereotype.Service

@Service
class CreateUrlUseCase {
  fun execute(): CreateUrlDto {
    return CreateUrlDto("", "")
  }
}
