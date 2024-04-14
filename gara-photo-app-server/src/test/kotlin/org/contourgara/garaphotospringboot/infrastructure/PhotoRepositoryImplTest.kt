package org.contourgara.garaphotospringboot.infrastructure

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.common.ResourceUtils
import org.contourgara.garaphotospringboot.domain.Media
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class PhotoRepositoryImplTest {
  @Test
  fun `引数の前日のディレクトリの写真を取得し、メディアを返す`() {
    // setup
    val sut = PhotoRepositoryImpl()
    val dateTime = ZonedDateTime.of(LocalDateTime.of(2024, 4, 23, 0, 0, 0), ZoneId.systemDefault())

    // execute
    val actual = sut.findForYesterday(dateTime)

    // assert
    val expected = Media(listOf(
      ResourceUtils.getFile("photo/20240422/1.jpg"),
      ResourceUtils.getFile("photo/20240422/2.jpg"),
      ResourceUtils.getFile("photo/20240422/3.jpg"),
      ResourceUtils.getFile("photo/20240422/4.jpg"),
      ))

    assertThat(actual).isEqualTo(expected)
  }
}
