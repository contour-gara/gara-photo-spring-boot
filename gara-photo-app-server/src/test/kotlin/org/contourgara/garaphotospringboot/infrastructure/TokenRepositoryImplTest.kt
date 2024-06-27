package org.contourgara.garaphotospringboot.infrastructure

import com.github.database.rider.core.api.configuration.DBUnit
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.core.api.dataset.ExpectedDataSet
import com.github.database.rider.junit5.api.DBRider
import org.assertj.core.api.Assertions.*
import org.contourgara.garaphotospringboot.domain.Token
import org.contourgara.garaphotospringboot.domain.infrastructure.TokenRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DuplicateKeyException
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

@SpringBootTest
@DBUnit
@DBRider
class TokenRepositoryImplTest {
  @Autowired
  lateinit var sut: TokenRepository

  @DataSet(value = ["datasets/setup/0-token.yml"])
  @ExpectedDataSet(value = ["datasets/expected/1-token.yml"])
  @Test
  fun `トークン情報をテーブルに保存できる`() {
    // execute
    sut.insert(Token("accessToken", "refreshToken", "clientId", ZonedDateTime.of(LocalDateTime.of(2024, 4, 14, 19, 48, 34, 0), ZoneId.systemDefault())))
  }

  @DataSet(value = ["datasets/setup/1-token.yml"])
  @ExpectedDataSet(value = ["datasets/expected/1-token.yml"])
  @Test
  fun `トークン情報の保存ですでにデータがある場合、例外が返る`() {
    // execute & assert
    assertThatThrownBy {
      sut.insert(Token("accessToken", "refreshToken", "clientId", ZonedDateTime.now()))
    }.isInstanceOf(DuplicateKeyException::class.java)
  }

  @DataSet(value = ["datasets/setup/1-token.yml"])
  @ExpectedDataSet(value = ["datasets/expected/1-token.yml"])
  @Test
  fun `トークン情報を取得できる`() {
    // execute
    val actual = sut.find("clientId")

    // assert
    val expected = Token("accessToken", "refreshToken", "clientId", ZonedDateTime.of(LocalDateTime.of(2024, 4, 14, 19, 48, 34, 0), ZoneId.systemDefault()))
    assertThat(actual).isEqualTo(expected)
  }

  @DataSet(value = ["datasets/setup/0-token.yml"])
  @ExpectedDataSet(value = ["datasets/expected/0-token.yml"])
  @Test
  fun `トークン情報を取得でレコードが無い場合、null が返る`() {
    // execute
    val actual = sut.find("clientId")

    // assert
    assertThat(actual).isNull()
  }

  @DataSet(value = ["datasets/setup/1-token.yml"])
  @ExpectedDataSet(value = ["datasets/expected/1-token-update.yml"])
  @Test
  fun `トークン情報を更新できる`() {
    // execute
    sut.update(Token("accessToken2", "refreshToken2", "clientId", ZonedDateTime.of(LocalDateTime.of(2024, 4, 14, 19, 48, 34, 0), ZoneId.systemDefault())))
  }
}
