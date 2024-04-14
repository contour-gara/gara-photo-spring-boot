package org.contourgara.garaphotospringboot.common

import org.springframework.core.io.DefaultResourceLoader
import java.io.File

object ResourceUtils {
  fun getFile(sourcePath: String): File {
    return DefaultResourceLoader().getResource("classpath:${sourcePath}").file
  }
}
