package org.contourgara.garaphotospringboot.domain.infrastructure

import org.contourgara.garaphotospringboot.domain.Token

interface AccessTokenRepository {
  fun insert(token: Token)
}
