package org.contourgara.garaphotospringboot.application

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
      "tweet.read%20users.read%20tweet.write%20offline.access",
      codeChallenge))

    return CreateUrlDto(url, codeChallenge)
  }
}
