package org.contourgara.garaphotospringboot.application.param

import org.contourgara.garaphotospringboot.domain.UploadedPhoto
import org.contourgara.garaphotospringboot.domain.UploadedYesterday

data class UploadYesterdayParam(
    val fileNames: List<String>,
    val bytes: List<ByteArray>,
) {
    fun convertToModel(): UploadedYesterday {
        return UploadedYesterday(fileNames.zip(bytes).map { UploadedPhoto(it.first, it.second) })
    }
}
