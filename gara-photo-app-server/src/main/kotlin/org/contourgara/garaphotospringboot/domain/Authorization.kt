package org.contourgara.garaphotospringboot.domain

data class Authorization(
    val clientId: String,
    val redirectUri: String,
    val code: String,
    val codeChallenge: String,
)
