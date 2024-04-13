package org.contourgara.garaphotospringboot.domain

data class AuthorizationSetting(
  val clientId: String,
  val redirectUri: String,
  val scope: List<String>,
  val codeChallenge: String,
  )
