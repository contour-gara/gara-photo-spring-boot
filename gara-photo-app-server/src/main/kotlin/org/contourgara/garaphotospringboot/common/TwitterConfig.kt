package org.contourgara.garaphotospringboot.common

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "twitter")
class TwitterConfig {
  var oauth1AccessToken: String = ""
  var oauth1AccessTokenSecret: String = ""
  var apiKey: String = ""
  var apiKeySecret: String = ""
  var clientId: String = ""
  var clientSecret: String = ""
  var redirectUri: String = ""
  var uploadBaseUrl: String = ""
}
