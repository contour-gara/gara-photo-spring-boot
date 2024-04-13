package org.contourgara.garaphotospringboot.common

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "twitter")
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
class TwitterConfig {
  var oauth1AccessToken: String = ""
  var oauth1AccessTokenSecret: String = ""
  var apiKey: String = ""
  var apiKeySecret: String = ""
  var clientId: String = ""
  var clientSecret: String = ""
  var redirectUri: String = ""
  var uploadBaseUrl: String = ""
  var codeChallenge: String = createChallenge()

  private fun createChallenge(): String {
    val chars = ('a'..'z') + (1..9)
    return (1..12).map {
      chars.random()
    }.joinToString("")
  }
}
