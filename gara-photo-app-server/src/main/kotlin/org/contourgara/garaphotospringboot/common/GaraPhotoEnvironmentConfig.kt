package org.contourgara.garaphotospringboot.common

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

@Configuration
class GaraPhotoEnvironmentConfig {
    @Profile(value = ["default", "prd"])
    @Bean
    fun realTimeClock(): Clock {
        return Clock.system(ZoneId.systemDefault())
    }

    @Profile(value = ["test"])
    @Bean
    fun fixedTimeClock(): Clock {
        return Clock.fixed(Instant.parse("2024-04-23T00:00:00+00:00"), ZoneId.of("UTC"))
    }
}
