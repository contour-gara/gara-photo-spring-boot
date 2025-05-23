package org.contourgara.garaphotospringboot.infrastructure

import com.github.database.rider.core.api.configuration.DBUnit
import com.github.database.rider.junit5.api.DBRider
import com.ninja_squad.dbsetup_kotlin.dbSetup
import com.zaxxer.hikari.HikariDataSource
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import org.assertj.db.api.Assertions.*
import org.assertj.db.type.AssertDbConnection
import org.assertj.db.type.AssertDbConnectionFactory
import org.assertj.db.type.DateTimeValue
import org.assertj.db.type.DateValue
import org.assertj.db.type.TimeValue
import org.contourgara.garaphotospringboot.domain.Token
import org.contourgara.garaphotospringboot.domain.infrastructure.TokenRepository
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DuplicateKeyException
import java.time.LocalDateTime

@SpringBootTest
@DBUnit
@DBRider
class TokenRepositoryImplTest(
    private val sut: TokenRepository,
    private val dataSource: HikariDataSource,
) : WordSpec() {
    lateinit var assertDbConnection: AssertDbConnection

    init {
        beforeSpec {
            assertDbConnection = AssertDbConnectionFactory.of(
                "jdbc:h2:mem:test;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false", "sa", "sa"
            ).create()
        }
        beforeEach {
            dbSetup(dataSource) {
                deleteAllFrom("token")
            }.launch()
        }

        "保存" When {
            "レコードに重複がない" should {
                "トークン情報をテーブルに保存できる" {
                    // setup
                    val changes = assertDbConnection.changes()
                        .tables(assertDbConnection.table("token").build())
                        .build()

                    // execute
                    changes.setStartPointNow()

                    sut.insert(
                        Token(
                            "accessToken",
                            "refreshToken",
                            "clientId",
                            LocalDateTime.of(2024, 4, 14, 19, 48, 34)
                        )
                    )

                    changes.setEndPointNow()

                    // assert
                    assertThat(changes)
                        .hasNumberOfChanges(1)
                        .change()
                        .isCreation()
                        .rowAtEndPoint()
                        .value("id").isEqualTo("1")
                        .value("access_token").isEqualTo("accessToken")
                        .value("refresh_Token").isEqualTo("refreshToken")
                        .value("date_time").isEqualTo(
                            DateTimeValue.of(DateValue.of(2024, 4, 14), TimeValue.of(19, 48, 34))
                        )
                }
            }

            "レコードに重複がある" should {
                "例外が返る" {
                    // setup
                    val changes = assertDbConnection.changes()
                        .tables(assertDbConnection.table("token").build())
                        .build()

                    dbSetup(dataSource) {
                        insertInto("token") {
                            columns("id", "access_token", "refresh_token", "date_time")
                            values("1", "accessToken", "refreshToken", LocalDateTime.of(2024, 4, 14, 19, 48, 34))
                        }
                    }.launch()

                    // execute & assert
                    changes.setStartPointNow()

                    shouldThrowExactly<DuplicateKeyException> {
                        sut.insert(Token("accessToken", "refreshToken", "clientId", LocalDateTime.now()))
                    }

                    changes.setEndPointNow()

                    assertThat(changes).hasNumberOfChanges(0)
                }
            }
        }

        "取得" When {
            "レコードあり" should {
                "トークン情報を取得できる" {
                    // setup
                    val changes = assertDbConnection.changes()
                        .tables(assertDbConnection.table("token").build())
                        .build()

                    dbSetup(dataSource) {
                        insertInto("token") {
                            columns("id", "access_token", "refresh_token", "date_time")
                            values("1", "accessToken", "refreshToken", "2024-04-14 19:48:34")
                        }
                    }.launch()

                    // execute & assert
                    changes.setStartPointNow()

                    sut.find("clientId") shouldBe Token(
                        "accessToken",
                        "refreshToken",
                        "clientId",
                        LocalDateTime.of(2024, 4, 14, 19, 48, 34)
                    )

                    changes.setEndPointNow()

                    assertThat(changes).hasNumberOfChanges(0)
                }
            }

            "レコードなし" should {
                "null が返る" {
                    // setup
                    val changes = assertDbConnection.changes()
                        .tables(assertDbConnection.table("token").build())
                        .build()

                    // execute & assert
                    changes.setStartPointNow()

                    sut.find("clientId").shouldBeNull()

                    changes.setEndPointNow()

                    assertThat(changes).hasNumberOfChanges(0)
                }
            }
        }

        "更新" When {
            "レコードがあある" should {
                "トークン情報を更新できる" {
                    // setup
                    val changes = assertDbConnection.changes()
                        .tables(assertDbConnection.table("token").build())
                        .build()

                    dbSetup(dataSource) {
                        insertInto("token") {
                            columns("id", "access_token", "refresh_token", "date_time")
                            values("1", "accessToken", "refreshToken", "2024-04-14 19:48:34")
                        }
                    }.launch()

                    // execute
                    changes.setStartPointNow()

                    sut.update(
                        Token(
                            "accessToken2",
                            "refreshToken2",
                            "clientId",
                            LocalDateTime.of(2024, 4, 14, 19, 48, 34)
                        )
                    )

                    changes.setEndPointNow()

                    // assert
                    assertThat(changes)
                        .hasNumberOfChanges(1)
                        .change()
                        .isModification()
                        .rowAtEndPoint()
                        .value("id").isEqualTo("1")
                        .value("access_token").isEqualTo("accessToken2")
                        .value("refresh_Token").isEqualTo("refreshToken2")
                        .value("date_time").isEqualTo(
                            DateTimeValue.of(DateValue.of(2024, 4, 14), TimeValue.of(19, 48, 34))
                        )
                }
            }
        }
    }
}
