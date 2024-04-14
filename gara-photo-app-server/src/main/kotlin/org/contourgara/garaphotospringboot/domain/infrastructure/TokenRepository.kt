package org.contourgara.garaphotospringboot.domain.infrastructure

import org.contourgara.garaphotospringboot.domain.Token

interface TokenRepository {
  fun insert(token: Token)
  fun find(clientId: String): Token?
  fun update(token: Token)
}
