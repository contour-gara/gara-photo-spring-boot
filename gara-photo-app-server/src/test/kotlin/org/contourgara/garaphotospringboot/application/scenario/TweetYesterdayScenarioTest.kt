package org.contourgara.garaphotospringboot.application.scenario

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.application.dto.FindTokenDto
import org.contourgara.garaphotospringboot.application.dto.TweetYesterdayDto
import org.contourgara.garaphotospringboot.application.usecase.FindTokenUseCase
import org.contourgara.garaphotospringboot.application.usecase.TweetYesterdayUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*

class TweetYesterdayScenarioTest {
    @InjectMocks
    lateinit var sut: TweetYesterdayScenario

    @Mock
    lateinit var findTokenUseCase: FindTokenUseCase

    @Mock
    lateinit var tweetYesterdayUseCase: TweetYesterdayUseCase

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `ツイート ID が返る`() {
        // setup
        doReturn(FindTokenDto("accessToken")).whenever(findTokenUseCase).execute()
        doReturn(TweetYesterdayDto("1")).whenever(tweetYesterdayUseCase).execute("accessToken")

        // execute
        val actual = sut.execute()

        // assert
        val expected = TweetYesterdayDto("1")
        assertThat(actual).isEqualTo(expected)
    }
}
