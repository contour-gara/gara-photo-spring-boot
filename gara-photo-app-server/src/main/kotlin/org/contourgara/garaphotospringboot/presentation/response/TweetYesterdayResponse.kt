package org.contourgara.garaphotospringboot.presentation.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.contourgara.garaphotospringboot.application.dto.TweetYesterdayDto

data class TweetYesterdayResponse(
    @JsonProperty("tweetId") val tweetId: String
) {
    companion object {
        fun of(tweetYesterdayDto: TweetYesterdayDto): TweetYesterdayResponse {
            return TweetYesterdayResponse(tweetYesterdayDto.tweetId)
        }
    }
}
