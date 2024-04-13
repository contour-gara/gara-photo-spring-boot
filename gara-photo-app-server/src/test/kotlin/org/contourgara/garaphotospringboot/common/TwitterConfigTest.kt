package org.contourgara.garaphotospringboot.common

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class TwitterConfigTest {
  @Autowired
  lateinit var twitterConfig: TwitterConfig

  @Test
  fun `テストプロファイルの場合、適切な値が設定されている`() {
    assertThat(twitterConfig.oauth1AccessToken).isEqualTo("oauth1-access-token-dummy")
  }
}
