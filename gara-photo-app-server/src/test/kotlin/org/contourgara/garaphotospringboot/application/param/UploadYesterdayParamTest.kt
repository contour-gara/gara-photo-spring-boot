package org.contourgara.garaphotospringboot.application.param

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import org.contourgara.garaphotospringboot.TestUtils
import org.contourgara.garaphotospringboot.domain.UploadedPhoto
import org.contourgara.garaphotospringboot.domain.UploadedYesterday

class UploadYesterdayParamTest : WordSpec({
    "ドメインモデルに変換" should {
        "ドメインモデルが返る" {
            // setup
            val photo = TestUtils.getFile("photo/yesterday/20240422/20240422-190001-01.png")
            val sut = UploadYesterdayParam(listOf(photo.name), listOf(photo.readBytes()))

            // execute & assert
            sut.convertToModel() shouldBe UploadedYesterday(listOf(UploadedPhoto(photo.name, photo.readBytes())))
        }
    }
})
