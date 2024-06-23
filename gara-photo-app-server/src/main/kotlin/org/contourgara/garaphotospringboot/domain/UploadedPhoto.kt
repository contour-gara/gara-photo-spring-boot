package org.contourgara.garaphotospringboot.domain

data class UploadedPhoto(
  val fileName: String,
  val byte: ByteArray
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as UploadedPhoto

    if (fileName != other.fileName) return false
    if (!byte.contentEquals(other.byte)) return false

    return true
  }

  override fun hashCode(): Int {
    var result = fileName.hashCode()
    result = 31 * result + byte.contentHashCode()
    return result
  }
}
