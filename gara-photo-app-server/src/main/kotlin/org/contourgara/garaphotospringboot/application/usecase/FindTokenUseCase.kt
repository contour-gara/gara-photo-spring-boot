package org.contourgara.garaphotospringboot.application.usecase

import org.contourgara.garaphotospringboot.application.dto.FindTokenDto
import org.contourgara.garaphotospringboot.application.exception.TokenNotFoundException
import org.contourgara.garaphotospringboot.common.GaraPhotoEnvironment
import org.contourgara.garaphotospringboot.common.TwitterConfig
import org.contourgara.garaphotospringboot.domain.Token
import org.contourgara.garaphotospringboot.domain.infrastructure.TokenProvider
import org.contourgara.garaphotospringboot.domain.infrastructure.TokenRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class FindTokenUseCase(
  private val twitterConfig: TwitterConfig,
  private val tokenProvider: TokenProvider,
  @Qualifier("mybatis") private val tokenRepository: TokenRepository,
  private val garaPhotoEnvironment: GaraPhotoEnvironment,
  ) {
  fun execute(): FindTokenDto {
    var token: Token = tokenRepository.find(twitterConfig.clientId) ?: throw TokenNotFoundException()

    if (token.isInvalid(garaPhotoEnvironment.getCurrentDateTime())) {
      token = tokenProvider.fetchTokenByRefreshToken(token)
      tokenRepository.update(token)
    }

    return FindTokenDto.of(token)
  }
}
