package org.contourgara.garaphotospringboot.infrastructure

import io.kotest.core.spec.style.WordSpec
import io.kotest.engine.spec.tempdir
import io.kotest.matchers.file.shouldExist
import io.kotest.matchers.shouldBe
import org.contourgara.garaphotospringboot.TestUtils
import org.contourgara.garaphotospringboot.domain.Media
import org.contourgara.garaphotospringboot.domain.PhotoYesterday
import org.contourgara.garaphotospringboot.domain.UploadedPhoto
import java.io.File

class PhotoRepositoryImplTest: WordSpec({
  "引数のディレクトリの写真を取得" should  {
    "メディアを返す" {
      // setup
      val sut = PhotoRepositoryImpl()

      // execute & assert
      sut.findForYesterday("classpath:photo/yesterday/20240422") shouldBe Media(listOf(
        TestUtils.getFile("photo/yesterday/20240422/20240422-190001-01.png"),
        TestUtils.getFile("photo/yesterday/20240422/20240422-190002-02.png"),
        TestUtils.getFile("photo/yesterday/20240422/20240422-190003-03.png"),
        TestUtils.getFile("photo/yesterday/20240422/20240422-190004-04.png"),
      ))
    }
  }

  "写真を引数のディレクトリに保存" should {
    "写真が保存されている" {
      // setup
      val sut = PhotoRepositoryImpl()

      val photo1 = TestUtils.getFile("photo/yesterday/20240422/20240422-190001-01.png")
      val photo2 = TestUtils.getFile("photo/yesterday/20240422/20240422-190002-02.png")
      val photoYesterday = PhotoYesterday(listOf(
        UploadedPhoto(photo1.name, photo1.readBytes()),
        UploadedPhoto(photo2.name, photo2.readBytes()),
      ))

      val tempDir = tempdir()

      // execute
      sut.saveForYesterday(photoYesterday, tempDir.absolutePath)

      // assert
      File("${tempDir.absolutePath}/20240422/${photo1.name}").shouldExist()
      File("${tempDir.absolutePath}/20240422/${photo2.name}").shouldExist()
    }
  }
})
