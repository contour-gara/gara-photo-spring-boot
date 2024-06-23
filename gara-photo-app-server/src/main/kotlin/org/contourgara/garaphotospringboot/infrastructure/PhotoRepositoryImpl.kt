package org.contourgara.garaphotospringboot.infrastructure

import org.contourgara.garaphotospringboot.domain.Media
import org.contourgara.garaphotospringboot.domain.PhotoYesterday
import org.contourgara.garaphotospringboot.domain.infrastructure.PhotoRepository
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path

@Component
class PhotoRepositoryImpl: PhotoRepository {
  override fun findForYesterday(sourcePath: String): Media {
    return Media(PathMatchingResourcePatternResolver().getResources("${sourcePath}/*").map { it.file }.toList())
  }

  override fun saveForYesterday(photoYesterday: PhotoYesterday, sourcePath: String) {
    val path = "${sourcePath}/${photoYesterday.getDate()}"
    Files.createDirectories(Path.of(path))
    photoYesterday.photos.forEach { Files.write(Path.of("${path}/${it.fileName}"), it.byte) }
  }
}
