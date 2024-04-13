package org.contourgara.garaphotospringboot.infrastructure

import org.contourgara.garaphotospringboot.domain.Token
import org.contourgara.garaphotospringboot.domain.infrastructure.AccessTokenRepository
import org.springframework.stereotype.Repository

@Repository
class TokenRepositoryImpl(
  private val tokenMapper: TokenMapper
): AccessTokenRepository {
  override fun insert(token: Token) {
    tokenMapper.insert(TokenEntity.of(token))
  }
}
