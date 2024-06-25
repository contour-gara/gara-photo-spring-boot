package org.contourgara.garaphotospringboot.domain

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.contourgara.garaphotospringboot.TestUtils.getFile

class UploadedPhotoTest: WordSpec({
  "インスタンスの比較" should {
    "同じである" {
      // setup
      val photo = getFile("photo/yesterday/20240619-100940-L1003318-LEICA M10 MONOCHROM.jpg")

      // execute & assert
      (UploadedPhoto(photo.name, photo.readBytes()) == UploadedPhoto(photo.name, photo.readBytes())).shouldBeTrue()
    }

    "異なっている" {
      // setup
      val photo1 = getFile("photo/yesterday/20240619-100940-L1003318-LEICA M10 MONOCHROM.jpg")
      val photo2 = getFile("photo/yesterday/20240620-193123-L1003326-LEICA M10 MONOCHROM.jpg")

      // execute & assert
      (UploadedPhoto(photo1.name, photo1.readBytes()) == UploadedPhoto(photo2.name, photo2.readBytes())).shouldBeFalse()

    }
  }
})
