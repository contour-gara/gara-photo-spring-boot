package org.contourgara.garaphotospringboot.infrastructure

import org.contourgara.garaphotospringboot.domain.Token
import org.contourgara.garaphotospringboot.domain.infrastructure.AccessTokenRepository
import org.springframework.stereotype.Repository

@Repository
class TokenRepositoryImpl(
  private val tokenMapper: TokenMapper
): AccessTokenRepository {
  override fun insert(token: Token) {
    // TODO: すでにレコードがある場合の例外処理
    tokenMapper.insert(TokenEntity.of(token))
  }
}
