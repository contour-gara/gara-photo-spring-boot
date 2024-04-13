package org.contourgara.garaphotospringboot.infrastructure

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.common.TwitterConfig
import org.contourgara.garaphotospringboot.domain.AuthorizationSetting
import org.contourgara.garaphotospringboot.domain.infrastructure.TokenProvider
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class TokenProviderImplTest {
  @InjectMocks
  lateinit var sut: TokenProviderImpl

  @Mock
  lateinit var twitterConfig: TwitterConfig

  @BeforeEach
  fun setUp() {
    MockitoAnnotations.openMocks(this)
  }

  @Test
  fun `認可 URL が生成できる`() {
    // setup
    val authorizationSetting = AuthorizationSetting("client-id-dummy", "http://localhost/dummy", listOf("dummy", "dummy"), "challenge")

    // execute
    val actual = sut.createUrl(authorizationSetting)

    // assert
    val expected = "https://twitter.com/i/oauth2/authorize?response_type=code&client_id=client-id-dummy&redirect_uri=http://localhost/dummy&scope=dummy%20dummy&state=state&code_challenge=challenge&code_challenge_method=plain"

    assertThat(actual).isEqualTo(expected)
  }
}
