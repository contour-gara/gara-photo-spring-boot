package org.contourgara.garaphotospringboot.infrastructure

import org.contourgara.garaphotospringboot.domain.Media
import org.contourgara.garaphotospringboot.domain.UploadedYesterday
import org.contourgara.garaphotospringboot.domain.infrastructure.PhotoRepository
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.isRegularFile

@Component
class PhotoRepositoryImpl : PhotoRepository {
    override fun findForYesterday(sourcePath: String): Media {
        return Media(PathMatchingResourcePatternResolver().getResources("$sourcePath/*").map { it.file }.toList())
    }

    override fun saveForYesterday(uploadedYesterday: UploadedYesterday, sourcePath: String) {
        val path = "$sourcePath/${uploadedYesterday.getDate()}"
        Files.createDirectories(Path.of(path))
        Files.walk(Path.of(path)).filter { it.isRegularFile() }.map { it.toFile() }.forEach { it.delete() }
        uploadedYesterday.photos.forEach { Files.write(Path.of("$path/${it.fileName}"), it.byte) }
    }
}
