package org.contourgara.garaphotospringboot.infrastructure

import org.contourgara.garaphotospringboot.domain.AuthorizationSetting
import org.contourgara.garaphotospringboot.domain.infrastructure.TokenProvider
import org.springframework.stereotype.Component
import twitter4j.OAuth2TokenProvider
import twitter4j.conf.ConfigurationBuilder

@Component
class TokenProviderImpl: TokenProvider {
  override fun createUrl(authorizationSetting: AuthorizationSetting): String {
    return OAuth2TokenProvider(ConfigurationBuilder().build()).createAuthorizeUrl(
      authorizationSetting.clientId,
      authorizationSetting.redirectUri,
      authorizationSetting.scope.toTypedArray(),
      authorizationSetting.codeChallenge)
  }
}
