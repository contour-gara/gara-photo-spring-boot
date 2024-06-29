package org.contourgara.garaphotospringboot.domain

import java.io.File

data class Media(val files: List<File>) {
    init {
        require(files.size <= MAX_FILE_COUNT) { "files length must be between 0 and 4. [length = ${files.size}]" }
    }

    companion object {
        private const val MAX_FILE_COUNT = 4
    }
}
