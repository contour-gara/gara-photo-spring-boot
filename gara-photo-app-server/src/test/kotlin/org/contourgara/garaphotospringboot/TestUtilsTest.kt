package org.contourgara.garaphotospringboot

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.IOException

class TestUtilsTest {
    @Test
    fun `ファイルが存在する場合、例外が投げられない`() {
        // execute & assert
        assertThatCode { TestUtils.getFile("photo/5photo/20240422-190001-01.png") }.doesNotThrowAnyException()
    }

    @Test
    fun `ファイルが存在しない場合、例外が投げられる`() {
        // execute & assert
        assertThatThrownBy {
            TestUtils.getFile("photo/5photo/20240422-190000-00.png")
        }.isInstanceOf(IOException::class.java)
    }
}
