package org.contourgara.garaphotospringboot.common

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

@ActiveProfiles("test")
@SpringBootTest
class GaraPhotoEnvironmentTest {
  @Autowired
  lateinit var sut: GaraPhotoEnvironment

  @Test
  fun `テストプロファイルで起動した場合、2024 年 4 月 23 日が返る`() {
    // execute
    val actual = sut.getCurrentDateTime()

    // assert
    val expected = ZonedDateTime.of(LocalDateTime.of(2024, 4, 23, 0, 0, 0), ZoneId.systemDefault())
    assertThat(actual).isEqualTo(expected)
  }
}
