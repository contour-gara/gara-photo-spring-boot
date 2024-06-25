package org.contourgara.garaphotospringboot.domain

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.TestUtils
import org.junit.jupiter.api.Test

class MediaTest {
  @Test
  fun `4 個の画像リストでインスタンスを生成できる`() {
    // setup
    val files = listOf(
      TestUtils.getFile("photo/test/1.jpg"),
      TestUtils.getFile("photo/test/2.jpg"),
      TestUtils.getFile("photo/test/3.jpg"),
      TestUtils.getFile("photo/test/4.jpg"),
      )

    // execute & assert
    assertThatCode { Media(files) }.doesNotThrowAnyException()
  }

  @Test
  fun `5 個の画像リストの場合、例外が飛ぶ`() {
    // setup
    val files = listOf(
      TestUtils.getFile("photo/test/1.jpg"),
      TestUtils.getFile("photo/test/2.jpg"),
      TestUtils.getFile("photo/test/3.jpg"),
      TestUtils.getFile("photo/test/4.jpg"),
      TestUtils.getFile("photo/test/5.jpg"),
    )

    // execute & assert
    assertThatThrownBy { Media(files) }
      .isInstanceOf(IllegalArgumentException::class.java)
      .hasMessage("files length must be between 0 and 4. [length = 5]")
  }
}
