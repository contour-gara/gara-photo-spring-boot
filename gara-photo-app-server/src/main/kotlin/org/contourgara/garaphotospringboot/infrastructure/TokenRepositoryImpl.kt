package org.contourgara.garaphotospringboot.infrastructure

import org.contourgara.garaphotospringboot.domain.Token
import org.contourgara.garaphotospringboot.domain.infrastructure.AccessTokenRepository
import org.springframework.stereotype.Repository

@Repository
class TokenRepositoryImpl(
  private val tokenMapper: TokenMapper
): AccessTokenRepository {
  override fun insert(token: Token) {
    println(TokenEntity.of(token).dateTime)
    tokenMapper.insert(TokenEntity.of(token))
  }
}
