package org.contourgara.garaphotospringboot.application

import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.application.dto.CreateUrlDto
import org.contourgara.garaphotospringboot.common.TwitterConfig
import org.contourgara.garaphotospringboot.domain.infrastructure.TokenProvider
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.*
import org.mockito.MockitoAnnotations

class CreateUrlUseCaseTest {
  @InjectMocks
  lateinit var sut: CreateUrlUseCase

  @Mock
  lateinit var tokenProvider: TokenProvider
  @Mock
  lateinit var twitterConfig: TwitterConfig

  @BeforeEach
  fun setUp() {
    MockitoAnnotations.openMocks(this)
  }

  @Test
  fun `URL と code_challenge を取得できる`() {
    // setup
    doReturn("url").whenever(tokenProvider).createUrl(any())
    doReturn("dummy").whenever(twitterConfig).clientId
    doReturn("dummy").whenever(twitterConfig).redirectUri
    doReturn("challenge").whenever(twitterConfig).codeChallenge

    // execute
    val actual = sut.execute()

    // assert
    val expected = CreateUrlDto("url", "challenge")

    assertThat(actual).isEqualTo(expected)
  }
}