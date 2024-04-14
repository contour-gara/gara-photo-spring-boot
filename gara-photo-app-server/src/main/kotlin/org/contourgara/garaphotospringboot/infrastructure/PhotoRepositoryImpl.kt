package org.contourgara.garaphotospringboot.infrastructure

import org.contourgara.garaphotospringboot.domain.Media
import org.contourgara.garaphotospringboot.domain.infrastructure.PhotoRepository
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Component
class PhotoRepositoryImpl: PhotoRepository {
  override fun findForYesterday(dateTime: ZonedDateTime): Media {
    val formattedDateTime: String = dateTime.minusDays(1L).toLocalDateTime().format(DateTimeFormatter.BASIC_ISO_DATE)
    return Media(PathMatchingResourcePatternResolver().getResources("classpath:photo/${formattedDateTime}/*").map { it.file }.toList())
  }
}
