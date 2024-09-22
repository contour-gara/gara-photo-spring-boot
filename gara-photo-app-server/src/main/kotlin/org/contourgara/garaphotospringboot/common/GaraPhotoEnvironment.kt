package org.contourgara.garaphotospringboot.common

import org.springframework.stereotype.Component
import java.time.Clock
import java.time.LocalDateTime

@Component
class GaraPhotoEnvironment(private val clock: Clock) {
    fun getCurrentDateTime(): LocalDateTime {
        return LocalDateTime.now(clock)
    }
}
