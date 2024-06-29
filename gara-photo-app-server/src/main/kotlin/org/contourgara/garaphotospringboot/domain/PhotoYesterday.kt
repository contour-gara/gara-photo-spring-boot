package org.contourgara.garaphotospringboot.domain

data class PhotoYesterday(
    val photos: List<UploadedPhoto>,
) {
    init {
        require(photos.isNotEmpty()) { "No photos yet" }
        require(photos.size <= 4) { "Photos must have at least 4 photos" }
        require(isSameDate()) { "Photo must be taken same date" }
    }

    fun getDate(): String {
        return photos.last().fileName.substring(0, 8)
    }

    private fun isSameDate(): Boolean {
        return photos.map { it.fileName.substring(0, 8) }.toSet().size == 1
    }
}
