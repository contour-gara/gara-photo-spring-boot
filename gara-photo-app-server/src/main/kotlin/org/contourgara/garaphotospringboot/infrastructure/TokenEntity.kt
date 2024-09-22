package org.contourgara.garaphotospringboot.infrastructure

import org.contourgara.garaphotospringboot.domain.Token
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

data class TokenEntity(
    val accessToken: String,
    val refreshToken: String,
    val dateTime: LocalDateTime,
) {
    companion object {
        fun of(token: Token): TokenEntity {
            return TokenEntity(
                token.accessToken,
                token.refreshToken,
                token.dateTime.toLocalDateTime()
            )
        }
    }

    fun convertToModel(clientId: String): Token {
        return Token(
            accessToken,
            refreshToken,
            clientId,
            ZonedDateTime.of(dateTime, ZoneId.systemDefault())
        )
    }
}
