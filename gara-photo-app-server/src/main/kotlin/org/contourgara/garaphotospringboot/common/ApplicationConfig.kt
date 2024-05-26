package org.contourgara.garaphotospringboot.common

import org.contourgara.garaphotospringboot.domain.infrastructure.TokenRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfig(
  private val applicationContext: ApplicationContext
) {
  @Bean
  fun tokenRepositoryAlias(@Value("\${application.repository}") qualifier: String): TokenRepository {
    return applicationContext.getBean(qualifier) as TokenRepository
  }
}
