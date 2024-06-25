package org.contourgara.garaphotospringboot.common

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.TestUtils
import org.junit.jupiter.api.Test
import java.io.IOException

class TestUtilsTest {
  @Test
  fun `ファイルが存在する場合、例外が投げられない`() {
    // execute & assert
    assertThatCode { TestUtils.getFile("photo/test/1.jpg") }.doesNotThrowAnyException()
  }

  @Test
  fun `ファイルが存在しない場合、例外が投げられる`() {
    // execute & assert
    assertThatThrownBy { TestUtils.getFile("photo/test/0.jpg") }.isInstanceOf(IOException::class.java)
  }
}
