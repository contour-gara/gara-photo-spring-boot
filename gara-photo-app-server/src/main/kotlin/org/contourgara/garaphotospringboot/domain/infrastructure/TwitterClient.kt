package org.contourgara.garaphotospringboot.domain.infrastructure

import org.contourgara.garaphotospringboot.domain.Tweet

interface TwitterClient {
  fun tweet(tweet: Tweet, accessToken: String): Long
}
