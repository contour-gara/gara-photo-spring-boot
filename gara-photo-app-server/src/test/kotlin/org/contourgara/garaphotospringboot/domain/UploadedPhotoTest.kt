package org.contourgara.garaphotospringboot.domain

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.WordSpec
import io.kotest.datatest.withData
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.contourgara.garaphotospringboot.TestUtils.getFile

class UploadedPhotoTest : WordSpec({
    data class TestPattern(val fileName: String, val expectedMessage: String)
    "ファイル名が不適切" should {
        withData(
            mapOf(
                "ファイル名が 8 文字未満の場合、例外が飛ぶ" to TestPattern("2024041", "ファイル名が短いです。"),
                "ファイル名の先頭が日付でない場合、例外が飛ぶ" to TestPattern("test1234", "ファイル名の先頭が yyyymmdd の形式になっていません。"),
            )
        ) { (fileName, expectedMessage) ->
            shouldThrowExactly<IllegalArgumentException> {
                UploadedPhoto(fileName, byteArrayOf())
            }.message shouldBe expectedMessage
        }
    }

    "getDate" should {
        "日付の文字列が返る" {
            // setup
            val photo = getFile("photo/yesterday/20240422/20240422-190001-01.png")
            val sut = UploadedPhoto(photo.name, photo.readBytes())

            // execute & assert
            sut.getDate() shouldBe "20240422"
        }
    }

    "インスタンスの比較" should {
        "同じである" {
            // setup
            val photo = getFile("photo/yesterday/20240422/20240422-190001-01.png")

            // execute & assert
            (UploadedPhoto(photo.name, photo.readBytes()) == UploadedPhoto(photo.name, photo.readBytes()))
                .shouldBeTrue()
        }

        "異なっている" {
            // setup
            val photo1 = getFile("photo/yesterday/20240422/20240422-190001-01.png")
            val photo2 = getFile("photo/yesterday/20240422/20240422-190002-02.png")

            // execute & assert
            (UploadedPhoto(photo1.name, photo1.readBytes()) == UploadedPhoto(photo2.name, photo2.readBytes()))
                .shouldBeFalse()
        }
    }
})
