package org.contourgara.garaphotospringboot

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class CalculationTest {
  val calculation = Calculation()

  @Test
  fun `掛け算ができる`() {
    val actual = calculation.multiplication(2, 3)
    assertThat(actual).isEqualTo(6)
  }
}
