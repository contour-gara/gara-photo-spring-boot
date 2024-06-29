package org.contourgara.garaphotospringboot

import org.springframework.core.io.DefaultResourceLoader
import java.io.File

object TestUtils {
    fun getFile(sourcePath: String): File {
        return DefaultResourceLoader().getResource("classpath:${sourcePath}").file
    }
}
