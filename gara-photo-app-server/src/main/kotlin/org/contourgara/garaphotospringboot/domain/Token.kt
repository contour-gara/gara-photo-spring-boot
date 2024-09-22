package org.contourgara.garaphotospringboot.domain

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class Token(
    val accessToken: String,
    val refreshToken: String,
    val clientId: String,
    val dateTime: LocalDateTime,
) {
    fun isInvalid(arg: LocalDateTime): Boolean {
        return ChronoUnit.SECONDS.between(dateTime, arg) >= TOKEN_EXPIRATION_SECONDS
    }

    companion object {
        private const val TOKEN_EXPIRATION_SECONDS = 6900
    }
}
