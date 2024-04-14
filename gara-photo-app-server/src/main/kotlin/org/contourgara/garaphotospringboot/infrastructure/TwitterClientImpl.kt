package org.contourgara.garaphotospringboot.infrastructure

import org.contourgara.garaphotospringboot.common.TwitterConfig
import org.contourgara.garaphotospringboot.domain.Tweet
import org.contourgara.garaphotospringboot.domain.infrastructure.TwitterClient
import org.springframework.stereotype.Component
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder
import twitter4j.v2

@Component
class TwitterClientImpl(private val twitterConfig: TwitterConfig): TwitterClient {
  override fun tweet(tweet: Tweet, accessToken: String): Long {
    val conf = ConfigurationBuilder()
      .setOAuthAccessToken(twitterConfig.oauth1AccessToken)
      .setOAuthAccessTokenSecret(twitterConfig.oauth1AccessTokenSecret)
      .setOAuthConsumerKey(twitterConfig.apiKey)
      .setOAuthConsumerSecret(twitterConfig.apiKeySecret)
      .setOAuth2AccessToken(accessToken)
      .build()

    val twitter = TwitterFactory(conf).instance

    val mediaIds = tweet.media.files.map { twitter.uploadMedia(it).mediaId }

    return twitter.v2.createTweet(text = tweet.text, mediaIds = mediaIds.toTypedArray()).id
  }
}
