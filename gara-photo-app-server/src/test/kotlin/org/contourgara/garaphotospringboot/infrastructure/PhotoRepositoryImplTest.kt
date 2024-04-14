package org.contourgara.garaphotospringboot.infrastructure

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.common.ResourceUtils
import org.contourgara.garaphotospringboot.domain.Media
import org.junit.jupiter.api.Test

class PhotoRepositoryImplTest {
  @Test
  fun `引数のディレクトリの写真を取得し、メディアを返す`() {
    // setup
    val sut = PhotoRepositoryImpl()

    // execute
    val actual = sut.findForYesterday("classpath:photo/20240422")

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
