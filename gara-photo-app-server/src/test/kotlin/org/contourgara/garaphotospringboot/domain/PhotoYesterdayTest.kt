package org.contourgara.garaphotospringboot.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import org.contourgara.garaphotospringboot.TestUtils.getFile

class PhotoYesterdayTest: WordSpec({
  "写真の日付の文字列を返す" should {
    // setup
    val photo = getFile("photo/yesterday/20240619-100940-L1003318-LEICA M10 MONOCHROM.jpg")
    val sut = PhotoYesterday(listOf(UploadedPhoto(photo.name, photo.readBytes())))

    // execute & assert
    sut.getDate() shouldBe "20240619"
  }

  "インスタンス生成" When {
    "アップロードする写真がない" should {
      "例外が返る" {
        // execute & assert
        shouldThrowExactly<IllegalArgumentException> {
          PhotoYesterday(emptyList())
        }.message shouldBe "No photos yet"
      }
    }

    "アップロードする写真が 5 つ以上" should {
      "例外が返る" {
        // setup
        val photo = getFile("photo/yesterday/20240619-100940-L1003318-LEICA M10 MONOCHROM.jpg")
        val uploaderPhoto = UploadedPhoto(photo.name, photo.readBytes())

        // assert & execute
        shouldThrow<IllegalArgumentException> {
          PhotoYesterday(listOf(uploaderPhoto, uploaderPhoto, uploaderPhoto, uploaderPhoto, uploaderPhoto))
        }.message shouldBe "Photos must have at least 4 photos"
      }
    }

    "アップロードする写真に異なる日の写真がある" should {
      "例外が返る" {
        // setup
        val photo20240619 = getFile("photo/yesterday/20240619-100940-L1003318-LEICA M10 MONOCHROM.jpg")
        val photo20240620 = getFile("photo/yesterday/20240620-192304-L1003325-LEICA M10 MONOCHROM.jpg")

        // execute & assert
        shouldThrow<IllegalArgumentException> {
          PhotoYesterday(listOf(
            UploadedPhoto(photo20240619.name, photo20240619.readBytes()),
            UploadedPhoto(photo20240620.name, photo20240620.readBytes())
          ))
        }.message shouldBe "Photo must be taken same date"
      }
    }
  }
})
