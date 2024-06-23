package org.contourgara.garaphotospringboot.application.param

import org.contourgara.garaphotospringboot.domain.PhotoYesterday
import org.contourgara.garaphotospringboot.domain.UploadedPhoto

data class UploadYesterdayParam(
  val fileNames: List<String>,
  val bytes: List<ByteArray>
) {
  fun convertToModel(): PhotoYesterday {
    return PhotoYesterday(fileNames.zip(bytes).map { UploadedPhoto(it.first, it.second) })
  }
}
