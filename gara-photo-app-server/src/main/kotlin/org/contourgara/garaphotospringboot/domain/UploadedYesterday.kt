package org.contourgara.garaphotospringboot.domain

data class UploadedYesterday(
    val photos: List<UploadedPhoto>,
) {
    init {
        require(photos.isNotEmpty()) { "No photos yet" }
        require(photos.size <= MAX_PHOTO_COUNT) { "Photos must have at least 4 photos" }
        require(isSameDate()) { "Photo must be taken same date" }
    }
    private fun isSameDate(): Boolean {
        return photos.map { it.getDate() }.toSet().size == 1
    }

    fun getDate(): String {
        return photos.first().getDate()
    }

    companion object {
        private const val MAX_PHOTO_COUNT = 4
    }
}
