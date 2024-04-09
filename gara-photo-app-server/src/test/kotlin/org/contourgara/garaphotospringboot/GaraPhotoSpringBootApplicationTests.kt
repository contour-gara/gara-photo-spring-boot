package org.contourgara.garaphotospringboot

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.presentation.OAuthController
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GaraPhotoSpringBootApplicationTests {
  @Autowired
  lateinit var oAuthController: OAuthController

  @Test
  fun contextLoads() {
    assertThat(oAuthController).isNotNull
  }
}
