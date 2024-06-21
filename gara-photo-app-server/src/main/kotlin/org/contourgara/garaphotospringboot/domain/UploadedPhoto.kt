package org.contourgara.garaphotospringboot.domain

data class UploadedPhoto(
  val fileName: String,
  val byte: ByteArray
)
