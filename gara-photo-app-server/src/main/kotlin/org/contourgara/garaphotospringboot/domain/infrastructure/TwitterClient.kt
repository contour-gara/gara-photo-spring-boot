package org.contourgara.garaphotospringboot.domain.infrastructure

import org.contourgara.garaphotospringboot.domain.Tweet

fun interface TwitterClient {
  fun tweet(tweet: Tweet, accessToken: String): Long
}
