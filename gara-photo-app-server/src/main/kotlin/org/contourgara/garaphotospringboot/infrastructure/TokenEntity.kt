package org.contourgara.garaphotospringboot.infrastructure

import org.contourgara.garaphotospringboot.domain.Token
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class TokenEntity(
  val accessToken: String,
  val refreshToken: String,
  val dateTime: String,
  ) {
  companion object {
    fun of(token: Token): TokenEntity {
      return TokenEntity(
        token.accessToken,
        token.refreshToken,
        token.dateTime.format(DateTimeFormatter.ISO_DATE_TIME)
      )
    }
  }

  fun convertToModel(clientId: String): Token {
    return Token(
      accessToken,
      refreshToken,
      clientId,
      ZonedDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME)
    )
  }
}
