package org.contourgara.garaphotospringboot.infrastructure

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.domain.AuthorizationSetting
import org.junit.jupiter.api.Test

class TokenProviderImplTest {
  @Test
  fun `認可 URL が生成できる`() {
    // setup
    val sut = TokenProviderImpl()
    val authorizationSetting = AuthorizationSetting("client-id-dummy", "http://localhost/dummy", listOf("dummy", "dummy"), "challenge")

    // execute
    val actual = sut.createUrl(authorizationSetting)

    // assert
    val expected = "https://twitter.com/i/oauth2/authorize?response_type=code&client_id=client-id-dummy&redirect_uri=http://localhost/dummy&scope=dummy%20dummy&state=state&code_challenge=challenge&code_challenge_method=plain"

    assertThat(actual).isEqualTo(expected)
  }
}
