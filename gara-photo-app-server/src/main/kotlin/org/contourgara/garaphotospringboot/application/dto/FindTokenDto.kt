package org.contourgara.garaphotospringboot.application.dto

import org.contourgara.garaphotospringboot.domain.Token

data class FindTokenDto(val accessToken: String) {
    companion object {
        fun of(token: Token): FindTokenDto {
            return FindTokenDto(token.accessToken)
        }
    }
}
