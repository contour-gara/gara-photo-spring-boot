package org.contourgara.garaphotospringboot.infrastructure

import org.contourgara.garaphotospringboot.domain.Authorization
import org.contourgara.garaphotospringboot.domain.AuthorizationSetting
import org.contourgara.garaphotospringboot.domain.Token
import org.contourgara.garaphotospringboot.domain.infrastructure.TokenProvider
import org.springframework.stereotype.Component
import twitter4j.OAuth2TokenProvider
import twitter4j.conf.ConfigurationBuilder
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Component
class TokenProviderImpl: TokenProvider {
  override fun createUrl(authorizationSetting: AuthorizationSetting): String {
    return OAuth2TokenProvider(ConfigurationBuilder().build()).createAuthorizeUrl(
      authorizationSetting.clientId,
      authorizationSetting.redirectUri,
      authorizationSetting.scope.toTypedArray(),
      authorizationSetting.codeChallenge)
  }

  override fun fetchToken(authorization: Authorization): Token {
    // TODO: 例外処理
    val result: OAuth2TokenProvider.Result = OAuth2TokenProvider(ConfigurationBuilder().build()).getAccessToken(
      authorization.clientId,
      authorization.redirectUri,
      authorization.code,
      authorization.codeChallenge) ?:throw RuntimeException()

    return Token(result.accessToken, result.refreshToken, authorization.clientId, ZonedDateTime.now())
  }
}
