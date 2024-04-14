package org.contourgara.garaphotospringboot.domain

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.common.ResourceUtils
import org.junit.jupiter.api.Test

class MediaTest {
  @Test
  fun `4 個の画像リストでインスタンスを生成できる`() {
    // setup
    val files = listOf(
      ResourceUtils.getFile("photo/20230422/1.jpg"),
      ResourceUtils.getFile("photo/20230422/2.jpg"),
      ResourceUtils.getFile("photo/20230422/3.jpg"),
      ResourceUtils.getFile("photo/20230422/4.jpg"),
      )

    // execute & assert
    assertThatCode { Media(files) }.doesNotThrowAnyException()
  }

  @Test
  fun `5 個の画像リストの場合、例外が飛ぶ`() {
    // setup
    val files = listOf(
      ResourceUtils.getFile("photo/20230422/1.jpg"),
      ResourceUtils.getFile("photo/20230422/2.jpg"),
      ResourceUtils.getFile("photo/20230422/3.jpg"),
      ResourceUtils.getFile("photo/20230422/4.jpg"),
      ResourceUtils.getFile("photo/20230422/5.jpg"),
    )

    // execute & assert
    assertThatThrownBy { Media(files) }
      .isInstanceOf(IllegalArgumentException::class.java)
      .hasMessage("files length must be between 0 and 4. [length = 5]")
  }
}
