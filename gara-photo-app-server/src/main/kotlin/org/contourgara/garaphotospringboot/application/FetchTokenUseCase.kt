package org.contourgara.garaphotospringboot.application

import org.contourgara.garaphotospringboot.application.param.FetchTokenParam
import org.contourgara.garaphotospringboot.common.TwitterConfig
import org.contourgara.garaphotospringboot.domain.Authorization
import org.contourgara.garaphotospringboot.domain.infrastructure.AccessTokenRepository
import org.contourgara.garaphotospringboot.domain.infrastructure.TokenProvider
import org.springframework.stereotype.Service

@Service
class FetchTokenUseCase(
  private val twitterConfig: TwitterConfig,
  private val tokenProvider: TokenProvider,
  private val accessTokenRepository: AccessTokenRepository
) {
  fun execute(fetchTokenParam: FetchTokenParam) {
    accessTokenRepository.insert(tokenProvider.fetchToken(
      Authorization(twitterConfig.clientId, twitterConfig.redirectUri, fetchTokenParam.code, fetchTokenParam.codeChallenge)
    ))
  }
}
