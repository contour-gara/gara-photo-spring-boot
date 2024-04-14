package org.contourgara.garaphotospringboot.domain

import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

data class Token(
  val accessToken: String,
  val refreshToken: String,
  val clientId: String,
  val dateTime: ZonedDateTime,
  ) {
  fun isInvalid(arg: ZonedDateTime): Boolean {
    return ChronoUnit.SECONDS.between(dateTime, arg) >= 6900
  }
}
