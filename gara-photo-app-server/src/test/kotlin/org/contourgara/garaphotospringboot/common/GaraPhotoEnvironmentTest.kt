package org.contourgara.garaphotospringboot.common

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime

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
        val expected = LocalDateTime.of(2024, 4, 23, 0, 0, 0)
        assertThat(actual).isEqualTo(expected)
    }
}
