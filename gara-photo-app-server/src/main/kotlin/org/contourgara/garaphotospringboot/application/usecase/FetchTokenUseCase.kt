package org.contourgara.garaphotospringboot.application.usecase

import org.contourgara.garaphotospringboot.application.param.FetchTokenParam
import org.contourgara.garaphotospringboot.common.TwitterConfig
import org.contourgara.garaphotospringboot.domain.Authorization
import org.contourgara.garaphotospringboot.domain.infrastructure.TokenRepository
import org.contourgara.garaphotospringboot.domain.infrastructure.TokenProvider
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class FetchTokenUseCase(
  private val twitterConfig: TwitterConfig,
  private val tokenProvider: TokenProvider,
  @Qualifier("tokenRepositoryAlias") private val tokenRepository: TokenRepository
) {
  fun execute(fetchTokenParam: FetchTokenParam) {
    tokenRepository.insert(tokenProvider.fetchToken(
      Authorization(twitterConfig.clientId, twitterConfig.redirectUri, fetchTokenParam.code, fetchTokenParam.codeChallenge)
    ))
  }
}
