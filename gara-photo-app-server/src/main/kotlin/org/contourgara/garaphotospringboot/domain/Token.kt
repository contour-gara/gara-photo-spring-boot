package org.contourgara.garaphotospringboot.domain

import java.time.ZonedDateTime

data class Token(
  val accessToken: String,
  val refreshToken: String,
  val clientId: String,
  val dateTime: ZonedDateTime,
  )
