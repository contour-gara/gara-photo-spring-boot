package org.contourgara.garaphotospringboot.domain.infrastructure

import org.contourgara.garaphotospringboot.domain.Media
import org.contourgara.garaphotospringboot.domain.UploadedYesterday

interface PhotoRepository {
    fun findForYesterday(sourcePath: String): Media
    fun saveForYesterday(uploadedYesterday: UploadedYesterday, sourcePath: String)
}
