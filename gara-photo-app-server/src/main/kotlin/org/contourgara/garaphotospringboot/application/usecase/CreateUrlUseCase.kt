package org.contourgara.garaphotospringboot.application.usecase

import org.contourgara.garaphotospringboot.application.dto.CreateUrlDto
import org.contourgara.garaphotospringboot.common.TwitterConfig
import org.contourgara.garaphotospringboot.domain.AuthorizationSetting
import org.contourgara.garaphotospringboot.domain.infrastructure.TokenProvider
import org.springframework.stereotype.Service

@Service
class CreateUrlUseCase(
  private val tokenProvider: TokenProvider,
  private val twitterConfig: TwitterConfig,
) {
  fun execute(): CreateUrlDto {
    val codeChallenge = twitterConfig.codeChallenge

    val url = tokenProvider.createUrl(AuthorizationSetting(
      twitterConfig.clientId,
      twitterConfig.redirectUri,
      listOf("tweet.read", "users.read", "tweet.write", "offline.access"),
      codeChallenge))

    return CreateUrlDto(url, codeChallenge)
  }
}
