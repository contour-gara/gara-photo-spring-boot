package org.contourgara.garaphotospringboot.infrastructure

import org.contourgara.garaphotospringboot.domain.Token
import java.time.LocalDateTime

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
                token.dateTime
            )
        }
    }

    fun convertToModel(clientId: String): Token {
        return Token(
            accessToken,
            refreshToken,
            clientId,
            dateTime
        )
    }
}
