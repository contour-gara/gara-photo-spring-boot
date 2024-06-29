package org.contourgara.garaphotospringboot.infrastructure

import org.contourgara.garaphotospringboot.common.GaraPhotoEnvironment
import org.contourgara.garaphotospringboot.common.TwitterConfig
import org.contourgara.garaphotospringboot.domain.Authorization
import org.contourgara.garaphotospringboot.domain.AuthorizationSetting
import org.contourgara.garaphotospringboot.domain.Token
import org.contourgara.garaphotospringboot.domain.infrastructure.TokenProvider
import org.springframework.stereotype.Component
import twitter4j.OAuth2TokenProvider
import twitter4j.conf.ConfigurationBuilder
import twitter4j.v2Configuration

@Component
class TokenProviderImpl(
    private val twitterConfig: TwitterConfig,
    private val garaPhotoEnvironment: GaraPhotoEnvironment,
) : TokenProvider {
    override fun createUrl(authorizationSetting: AuthorizationSetting): String {
        return OAuth2TokenProvider(ConfigurationBuilder().build()).createAuthorizeUrl(
            authorizationSetting.clientId,
            authorizationSetting.redirectUri,
            authorizationSetting.scope.toTypedArray(),
            authorizationSetting.codeChallenge
        )
    }

    override fun fetchToken(authorization: Authorization): Token {
        val conf = ConfigurationBuilder()
            .setOAuthAccessToken(twitterConfig.oauth1AccessToken)
            .setOAuthAccessTokenSecret(twitterConfig.oauth1AccessTokenSecret)
            .setOAuthConsumerKey(twitterConfig.clientId)
            .setOAuthConsumerSecret(twitterConfig.clientSecret)
            .build()

        conf.v2Configuration.baseURL = twitterConfig.oauth2RestBaseUrl

        // TODO: 例外処理
        val result: OAuth2TokenProvider.Result = OAuth2TokenProvider(conf).getAccessToken(
            authorization.clientId,
            authorization.redirectUri,
            authorization.code,
            authorization.codeChallenge
        ) ?: throw RuntimeException()

        return Token(
            result.accessToken,
            result.refreshToken,
            authorization.clientId,
            garaPhotoEnvironment.getCurrentDateTime()
        )
    }

    override fun fetchTokenByRefreshToken(token: Token): Token {
        val conf = ConfigurationBuilder()
            .setOAuthAccessToken(twitterConfig.oauth1AccessToken)
            .setOAuthAccessTokenSecret(twitterConfig.oauth1AccessTokenSecret)
            .setOAuthConsumerKey(twitterConfig.clientId)
            .setOAuthConsumerSecret(twitterConfig.clientSecret)
            .build()

        conf.v2Configuration.baseURL = twitterConfig.oauth2RestBaseUrl

        val result: OAuth2TokenProvider.Result = OAuth2TokenProvider(conf).refreshToken(
            token.clientId,
            token.refreshToken
        ) ?: throw RuntimeException()

        return Token(result.accessToken, result.refreshToken, token.clientId, garaPhotoEnvironment.getCurrentDateTime())
    }
}
