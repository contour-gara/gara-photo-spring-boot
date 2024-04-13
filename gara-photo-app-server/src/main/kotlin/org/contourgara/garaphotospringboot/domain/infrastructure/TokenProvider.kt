package org.contourgara.garaphotospringboot.domain.infrastructure

import org.contourgara.garaphotospringboot.domain.AuthorizationSetting

interface TokenProvider {
  fun createUrl(authorizationSetting: AuthorizationSetting): String
}
