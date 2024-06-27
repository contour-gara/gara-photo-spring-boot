package org.contourgara.garaphotospringboot.application.usecase

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.application.dto.TweetYesterdayDto
import org.contourgara.garaphotospringboot.common.GaraPhotoEnvironment
import org.contourgara.garaphotospringboot.TestUtils.getFile
import org.contourgara.garaphotospringboot.domain.Media
import org.contourgara.garaphotospringboot.domain.Tweet
import org.contourgara.garaphotospringboot.domain.infrastructure.PhotoRepository
import org.contourgara.garaphotospringboot.domain.infrastructure.TwitterClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class TweetYesterdayUseCaseTest {
  @InjectMocks
  lateinit var sut: TweetYesterdayUseCase

  @Mock
  lateinit var garaPhotoEnvironment: GaraPhotoEnvironment
  @Mock
  lateinit var photoRepository: PhotoRepository
  @Mock
  lateinit var twitterClient: TwitterClient

  @BeforeEach
  fun setUp() {
    MockitoAnnotations.openMocks(this)
  }

  @Test
  fun `ツイート ID が返る`() {
    // setup
    val dateTime = ZonedDateTime.of(LocalDateTime.of(2024, 4, 23, 0, 0, 0), ZoneId.systemDefault())
    val media = Media(listOf(getFile("photo/test/1.jpg")))

    doReturn(dateTime).whenever(garaPhotoEnvironment).getCurrentDateTime()
    doReturn(media).whenever(photoRepository).findForYesterday("file:///opt/photo/yesterday/20240422")
    doReturn(1L).whenever(twitterClient).tweetWithMedia(Tweet("yesterday", media), "accessToken")

    // execute
    val actual = sut.execute("accessToken")

    // assert
    val expected = TweetYesterdayDto("1")
    assertThat(actual).isEqualTo(expected)
  }
}
