package org.contourgara.garaphotospringboot.domain

import java.time.LocalDateTime

data class Token(
  val accessToken: String,
  val refreshToken: String,
  val clientId: String,
  val dateTime: LocalDateTime,
  )
