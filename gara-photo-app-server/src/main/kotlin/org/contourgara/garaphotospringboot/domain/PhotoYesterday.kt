package org.contourgara.garaphotospringboot.domain

data class PhotoYesterday(
    val photos: List<UploadedPhoto>,
) {
    init {
        require(photos.isNotEmpty()) { "No photos yet" }
        require(photos.size <= MAX_PHOTO_COUNT) { "Photos must have at least 4 photos" }
        require(isSameDate()) { "Photo must be taken same date" }
    }

    fun getDate(): String {
        return photos.last().fileName.substring(0, DATE_DIGIT)
    }

    private fun isSameDate(): Boolean {
        return photos.map { it.fileName.substring(0, DATE_DIGIT) }.toSet().size == 1
    }

    companion object {
        private const val MAX_PHOTO_COUNT = 4
        private const val DATE_DIGIT = 8
    }
}
