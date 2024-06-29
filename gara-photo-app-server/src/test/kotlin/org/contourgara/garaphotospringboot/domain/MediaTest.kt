package org.contourgara.garaphotospringboot.domain

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.TestUtils
import org.junit.jupiter.api.Test

class MediaTest {
    @Test
    fun `4 個の画像リストでインスタンスを生成できる`() {
        // setup
        val files = listOf(
            TestUtils.getFile("photo/5photo/20240422-190001-01.png"),
            TestUtils.getFile("photo/5photo/20240422-190002-02.png"),
            TestUtils.getFile("photo/5photo/20240422-190003-03.png"),
            TestUtils.getFile("photo/5photo/20240422-190004-04.png"),
        )

        // execute & assert
        assertThatCode { Media(files) }.doesNotThrowAnyException()
    }

    @Test
    fun `5 個の画像リストの場合、例外が飛ぶ`() {
        // setup
        val files = listOf(
            TestUtils.getFile("photo/5photo/20240422-190001-01.png"),
            TestUtils.getFile("photo/5photo/20240422-190002-02.png"),
            TestUtils.getFile("photo/5photo/20240422-190003-03.png"),
            TestUtils.getFile("photo/5photo/20240422-190004-04.png"),
            TestUtils.getFile("photo/5photo/20240422-190005-05.png"),
        )

        // execute & assert
        assertThatThrownBy { Media(files) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("files length must be between 0 and 4. [length = 5]")
    }
}
