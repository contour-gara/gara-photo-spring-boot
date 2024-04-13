package org.contourgara.garaphotospringboot.infrastructure

import org.contourgara.garaphotospringboot.domain.Token
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class TokenRepositoryImplTest {
  @InjectMocks
  lateinit var sut: TokenRepositoryImpl

  @Mock
  lateinit var tokenMapper: TokenMapper

  @BeforeEach
  fun setUp() {
    MockitoAnnotations.openMocks(this)
  }

  @Test
  fun `トークンを保存するためにマッパーを呼び出す`() {
    // setup
    val token = Token("accessToken", "refreshTOken", "clientId", ZonedDateTime.of(LocalDateTime.of(2024, 4, 14, 5, 13, 0), ZoneId.systemDefault()))

    // execute
    sut.insert(token)

    // assert
    verify(tokenMapper, times(1)).insert(any())
  }
}
