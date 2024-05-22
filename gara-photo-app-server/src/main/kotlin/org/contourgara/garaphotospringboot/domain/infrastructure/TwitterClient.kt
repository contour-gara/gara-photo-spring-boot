package org.contourgara.garaphotospringboot.domain.infrastructure

import org.contourgara.garaphotospringboot.domain.Tweet

fun interface TwitterClient {
  fun tweetWithMedia(tweet: Tweet, accessToken: String): Long
}
