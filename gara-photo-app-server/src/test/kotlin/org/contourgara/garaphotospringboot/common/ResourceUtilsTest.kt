package org.contourgara.garaphotospringboot.common

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.IOException

class ResourceUtilsTest {
  @Test
  fun `ファイルが存在する場合、例外が投げられない`() {
    // execute & assert
    assertThatCode { ResourceUtils.getFile("photo/20230422/1.jpg") }.doesNotThrowAnyException()
  }

  @Test
  fun `ファイルが存在しない場合、例外が投げられる`() {
    // execute & assert
    assertThatThrownBy { ResourceUtils.getFile("photo/20230422/0.jpg") }.isInstanceOf(IOException::class.java)
  }
}
