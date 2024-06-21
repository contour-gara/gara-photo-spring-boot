package org.contourgara.garaphotospringboot.domain

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.common.ResourceUtils.getFile
import org.junit.jupiter.api.Test

class PhotoYesterdayTest {
  @Test
  fun `空のリスト`() {
    // execute & assert
    assertThatThrownBy { PhotoYesterday(emptyList()) }
      .isInstanceOf(IllegalArgumentException::class.java)
      .hasMessage("No photos yet")
  }

  @Test
  fun `違う日`() {
    // setup
    val photos = listOf(
      getFile("photo/yesterday/20240619-100940-L1003318-LEICA M10 MONOCHROM.jpg"),
      getFile("photo/yesterday/20240620-192304-L1003325-LEICA M10 MONOCHROM.jpg"),
    )
    // execute & assert
    assertThatThrownBy { PhotoYesterday(photos) }
      .isInstanceOf(IllegalArgumentException::class.java)
      .hasMessage("Photo must be taken same date")
  }

  @Test
  fun `日付を返す`() {
    // setup
    val photos = listOf(
      getFile("photo/yesterday/20240619-100940-L1003318-LEICA M10 MONOCHROM.jpg"),
    )
    val sut = PhotoYesterday(photos)

    // execute
    val actual = sut.getDate()

    // assert
    val expected = "20240619"
    assertThat(actual).isEqualTo(expected)
  }
}
