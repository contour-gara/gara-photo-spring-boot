package org.contourgara.garaphotospringboot.domain

import java.io.File

data class Media(val files: List<File>) {
  init {
    require(files.size <= 4) {"files length must be between 0 and 4. [length = ${files.size}]"}
  }
}
