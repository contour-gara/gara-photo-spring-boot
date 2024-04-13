package org.contourgara.garaphotospringboot.infrastructure

import org.contourgara.garaphotospringboot.domain.Token
import org.contourgara.garaphotospringboot.domain.infrastructure.AccessTokenRepository
import org.springframework.stereotype.Repository

@Repository
class AccessTokenRepositoryImpl: AccessTokenRepository {
  override fun insert(token: Token) {
    TODO("Not yet implemented")
  }
}
