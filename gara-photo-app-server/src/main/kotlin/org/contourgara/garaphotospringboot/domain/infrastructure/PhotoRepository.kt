package org.contourgara.garaphotospringboot.domain.infrastructure

import org.contourgara.garaphotospringboot.domain.Media
import java.time.ZonedDateTime

interface PhotoRepository {
  fun findForYesterday(dateTime: ZonedDateTime): Media
}
