package org.contourgara.garaphotospringboot.domain

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class UploadedPhoto(
    val fileName: String,
    val byte: ByteArray,
) {
    init {
        require(fileName.length >= DATE_DIGIT) { "ファイル名が短いです。" }
        require(hasDate()) { "ファイル名の先頭が yyyymmdd の形式になっていません。" }
    }

    fun getDate(): String {
        return fileName.substring(0, DATE_DIGIT)
    }

    private fun hasDate(): Boolean {
        try {
            LocalDate.parse(fileName.substring(0, DATE_DIGIT), DateTimeFormatter.BASIC_ISO_DATE)
        } catch (e: DateTimeParseException) {
            return false
        }
        return true
    }

    companion object {
        private const val DATE_DIGIT = 8
    }

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
