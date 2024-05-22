package org.contourgara.garaphotospringboot.application.usecase

import org.contourgara.garaphotospringboot.application.dto.TweetYesterdayDto
import org.contourgara.garaphotospringboot.common.GaraPhotoEnvironment
import org.contourgara.garaphotospringboot.domain.Media
import org.contourgara.garaphotospringboot.domain.Tweet
import org.contourgara.garaphotospringboot.domain.infrastructure.PhotoRepository
import org.contourgara.garaphotospringboot.domain.infrastructure.TwitterClient
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter

@Service
class TweetYesterdayUseCase(
  private val garaPhotoEnvironment: GaraPhotoEnvironment,
  private val photoRepository: PhotoRepository,
  private val twitterClient: TwitterClient,
  ) {
  fun execute(accessToken: String): TweetYesterdayDto {
    val media: Media = photoRepository.findForYesterday(
      "file:///opt/photo/${garaPhotoEnvironment.getCurrentDateTime().minusDays(1L).toLocalDateTime().format(DateTimeFormatter.BASIC_ISO_DATE)}"
    )
    val tweetId: Long = twitterClient.tweetWithMedia(Tweet("yesterday", media), accessToken)
    return TweetYesterdayDto(tweetId.toString())
  }
}
