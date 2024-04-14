package org.contourgara.garaphotospringboot.domain.infrastructure

import org.contourgara.garaphotospringboot.domain.Authorization
import org.contourgara.garaphotospringboot.domain.AuthorizationSetting
import org.contourgara.garaphotospringboot.domain.Token

interface TokenProvider {
  fun createUrl(authorizationSetting: AuthorizationSetting): String
  fun fetchToken(authorization: Authorization): Token
  fun fetchTokenByRefreshToken(token: Token): Token
}
