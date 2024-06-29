package org.contourgara.garaphotospringboot.infrastructure

import org.contourgara.garaphotospringboot.domain.Token
import org.contourgara.garaphotospringboot.domain.infrastructure.TokenRepository
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Repository

@Suppress("ForbiddenComment")
@Repository
@ConditionalOnProperty(
    prefix = "application",
    name = ["repository"],
    havingValue = "mybatis",
)
class TokenRepositoryImplOld(
    private val tokenMapper: TokenMapper
) : TokenRepository {
    override fun insert(token: Token) {
        // TODO: すでにレコードがある場合の例外処理
        tokenMapper.insert(TokenEntity.of(token))
    }

    override fun find(clientId: String): Token? {
        return tokenMapper.find()?.convertToModel(clientId)
    }

    override fun update(token: Token) {
        tokenMapper.update(TokenEntity.of(token))
    }
}
