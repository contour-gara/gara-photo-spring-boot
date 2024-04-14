package org.contourgara.garaphotospringboot.common

import org.springframework.stereotype.Component
import java.time.Clock
import java.time.ZonedDateTime

@Component
class GaraPhotoEnvironment(private val clock: Clock) {
  fun getCurrentDateTime(): ZonedDateTime {
    return ZonedDateTime.now(clock)
  }
}
