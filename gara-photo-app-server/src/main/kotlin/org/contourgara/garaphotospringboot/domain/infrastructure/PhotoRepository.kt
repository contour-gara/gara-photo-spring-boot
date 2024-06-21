package org.contourgara.garaphotospringboot.domain.infrastructure

import org.contourgara.garaphotospringboot.domain.Media
import org.contourgara.garaphotospringboot.domain.PhotoYesterday

interface PhotoRepository {
  fun findForYesterday(sourcePath: String): Media
  fun saveForYesterday(photoYesterday: PhotoYesterday, sourcePath: String)
}
