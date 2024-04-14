package org.contourgara.garaphotospringboot.infrastructure

import org.contourgara.garaphotospringboot.domain.Media
import org.contourgara.garaphotospringboot.domain.infrastructure.PhotoRepository
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.stereotype.Component

@Component
class PhotoRepositoryImpl: PhotoRepository {
  override fun findForYesterday(sourcePath: String): Media {
    return Media(PathMatchingResourcePatternResolver().getResources("${sourcePath}/*").map { it.file }.toList())
  }
}
