package org.contourgara.garaphotospringboot.application.param

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import org.contourgara.garaphotospringboot.TestUtils
import org.contourgara.garaphotospringboot.domain.PhotoYesterday
import org.contourgara.garaphotospringboot.domain.UploadedPhoto

class UploadYesterdayParamTest: WordSpec({
  "ドメインモデルに変換" should {
    "ドメインモデルが返る" {
      // setup
      val photo = TestUtils.getFile("photo/yesterday/20240620-192304-L1003325-LEICA M10 MONOCHROM.jpg")
      val sut = UploadYesterdayParam(listOf(photo.name), listOf(photo.readBytes()))

      // execute & assert
      sut.convertToModel() shouldBe PhotoYesterday(listOf(UploadedPhoto(photo.name, photo.readBytes())))
    }
  }
})
