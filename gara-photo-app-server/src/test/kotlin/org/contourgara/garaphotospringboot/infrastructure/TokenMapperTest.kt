package org.contourgara.garaphotospringboot.infrastructure

import com.github.database.rider.core.api.configuration.DBUnit
import com.github.database.rider.core.api.connection.ConnectionHolder
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.core.api.dataset.ExpectedDataSet
import com.github.database.rider.junit5.api.DBRider
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DuplicateKeyException
import java.sql.DriverManager

@DBRider
@DBUnit
@SpringBootTest
class TokenMapperTest {
  companion object {
    private const val DB_URL: String = "jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false"
    private const val DB_USERNAME: String = "sa"
    private const val DB_PASSWORD: String = "sa"

    private val connectionHolder: ConnectionHolder = ConnectionHolder {
      DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)
    }
  }

  @Autowired
  lateinit var sut: TokenMapper

  @DataSet(value = ["datasets/setup/0-token.yml"])
  @ExpectedDataSet(value = ["datasets/expected/1-token.yml"])
  @Test
  fun `トークン情報をテーブルに保存できる`() {
    // setup
    val tokenEntity = TokenEntity("accessToken", "refreshToken", "2024-04-14T19:48:34.000+09:00[Asia/Tokyo]")

    // execute
    sut.insert(tokenEntity)
  }

  @DataSet(value = ["datasets/setup/1-token.yml"])
  @ExpectedDataSet(value = ["datasets/expected/1-token.yml"])
  @Test
  fun `トークン情報の保存ですでにデータがある場合、例外が返る`() {
    // setup
    val tokenEntity = TokenEntity("accessToken2", "refreshToken2", "2024-04-14T19:48:34.000+09:00[Asia/Tokyo]")

    // execute & assert
    assertThatThrownBy { sut.insert(tokenEntity) }.isInstanceOf(DuplicateKeyException::class.java)
  }

  @DataSet(value = ["datasets/setup/1-token.yml"])
  @ExpectedDataSet(value = ["datasets/expected/1-token.yml"])
  @Test
  fun `トークン情報を取得できる`() {
    // execute
    val actual = sut.find()

    // assert
    val expected = TokenEntity("accessToken", "refreshToken", "2024-04-14T19:48:34.000+09:00[Asia/Tokyo]")
    assertThat(actual).isEqualTo(expected)
  }

  @DataSet(value = ["datasets/setup/0-token.yml"])
  @ExpectedDataSet(value = ["datasets/expected/0-token.yml"])
  @Test
  fun `トークン情報を取得でレコードが無い場合、null が返る`() {
    // execute
    val actual = sut.find()

    // assert
    assertThat(actual).isNull()
  }
}
