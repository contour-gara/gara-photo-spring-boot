package org.contourgara.garaphotospringboot.presentation.response

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.application.dto.TweetYesterdayDto
import org.junit.jupiter.api.Test

class TweetYesterdayResponseTest {
    @Test
    fun `DTO からインスタンスを生成できる`() {
        // setup
        val tweetYesterdayDto = TweetYesterdayDto("1")

        // execute
        val actual = TweetYesterdayResponse.of(tweetYesterdayDto)

        // assert
        val expected = TweetYesterdayResponse("1")
        assertThat(actual).isEqualTo(expected)
    }
}
