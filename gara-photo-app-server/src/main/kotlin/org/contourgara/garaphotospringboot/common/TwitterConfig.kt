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
    var oauth2AccessToken: String = ""
    var oauth2RefreshToken: String = ""
    var apiKey: String = ""
    var apiKeySecret: String = ""
    var clientId: String = ""
    var clientSecret: String = ""
    var redirectUri: String = ""
    var uploadBaseUrl: String = ""
    var oauth2RestBaseUrl: String = ""
    var codeChallenge: String = createChallenge()

    private fun createChallenge(): String {
        val chars = ('a'..'z') + (1..MAX_SINGLE_DIGIT_NUMBER)
        return (1..MIN_DIGIT_CODE_CHALLENGE).map {
            chars.random()
        }.joinToString("")
    }

    companion object {
        private const val MAX_SINGLE_DIGIT_NUMBER = 9
        private const val MIN_DIGIT_CODE_CHALLENGE = 12
    }
}
