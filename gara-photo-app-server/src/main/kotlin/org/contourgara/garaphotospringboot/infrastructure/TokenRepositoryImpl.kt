package org.contourgara.garaphotospringboot.infrastructure

import org.contourgara.garaphotospringboot.domain.Token
import org.contourgara.garaphotospringboot.domain.infrastructure.TokenRepository
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository

@Repository
@ConditionalOnProperty(
    prefix = "application",
    name = ["repository"],
    havingValue = "jdbc-client",
    matchIfMissing = true,
)
class TokenRepositoryImpl(
    private val jdbcClient: JdbcClient
) : TokenRepository {
    override fun insert(token: Token) {
        val tokenEntity = TokenEntity.of(token)
        jdbcClient.sql("INSERT INTO token (id, access_token, refresh_token, date_time) VALUES (1, ?, ?, ?)")
            .param(tokenEntity.accessToken)
            .param(tokenEntity.refreshToken)
            .param(tokenEntity.dateTime)
            .update()
    }

    override fun find(clientId: String): Token? {
        return jdbcClient.sql("SELECT access_token, refresh_token, date_time FROM token WHERE id = 1")
            .query(DataClassRowMapper(TokenEntity::class.java))
            .optional()
            .orElse(null)
            ?.convertToModel(clientId)
    }

    override fun update(token: Token) {
        val tokenEntity = TokenEntity.of(token)
        jdbcClient.sql("UPDATE token SET access_token = ?, refresh_token = ?, date_time = ? WHERE id = 1")
            .param(tokenEntity.accessToken)
            .param(tokenEntity.refreshToken)
            .param(tokenEntity.dateTime)
            .update()
    }
}
