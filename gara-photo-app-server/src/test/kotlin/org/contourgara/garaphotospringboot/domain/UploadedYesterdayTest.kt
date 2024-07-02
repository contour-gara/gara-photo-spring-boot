package org.contourgara.garaphotospringboot.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import org.contourgara.garaphotospringboot.TestUtils.getFile

class UploadedYesterdayTest : WordSpec({
    "写真の日付の文字列を返す" should {
        // setup
        val photo = getFile("photo/yesterday/20240422/20240422-190001-01.png")
        val sut = UploadedYesterday(listOf(UploadedPhoto(photo.name, photo.readBytes())))

        // execute & assert
        sut.getDate() shouldBe "20240422"
    }

    "インスタンス生成" When {
        "アップロードする写真がない" should {
            "例外が返る" {
                // execute & assert
                shouldThrowExactly<IllegalArgumentException> {
                    UploadedYesterday(emptyList())
                }.message shouldBe "No photos yet"
            }
        }

        "アップロードする写真が 5 つ以上" should {
            "例外が返る" {
                // setup
                val photo = getFile("photo/yesterday/20240422/20240422-190001-01.png")
                val uploaderPhoto = UploadedPhoto(photo.name, photo.readBytes())

                // assert & execute
                shouldThrow<IllegalArgumentException> {
                    UploadedYesterday(listOf(uploaderPhoto, uploaderPhoto, uploaderPhoto, uploaderPhoto, uploaderPhoto))
                }.message shouldBe "Photos must have at least 4 photos"
            }
        }

        "アップロードする写真に異なる日の写真がある" should {
            "例外が返る" {
                // setup
                val photo20240619 = getFile("photo/yesterday/20240422/20240422-190001-01.png")
                val photo20240620 = getFile("photo/yesterday/20240421/20240421-190001-01.png")

                // execute & assert
                shouldThrow<IllegalArgumentException> {
                    UploadedYesterday(
                        listOf(
                            UploadedPhoto(photo20240619.name, photo20240619.readBytes()),
                            UploadedPhoto(photo20240620.name, photo20240620.readBytes())
                        )
                    )
                }.message shouldBe "Photo must be taken same date"
            }
        }
    }
})
