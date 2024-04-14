package org.contourgara.garaphotospringboot.domain.infrastructure

import org.contourgara.garaphotospringboot.domain.Media

fun interface PhotoRepository {
  fun findForYesterday(sourcePath: String): Media
}
