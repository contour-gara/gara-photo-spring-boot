package org.contourgara.garaphotospringboot.application.usecase

import io.kotest.core.spec.style.WordSpec
import org.contourgara.garaphotospringboot.application.param.UploadYesterdayParam
import org.contourgara.garaphotospringboot.domain.infrastructure.PhotoRepository
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*

class UploadYesterdayUseCaseTest : WordSpec() {
    @InjectMocks
    lateinit var sut: UploadYesterdayUseCase

    @Mock
    lateinit var photoRepository: PhotoRepository

    init {
        beforeEach {
            MockitoAnnotations.openMocks(this)
        }

        "実行" should {
            "リポジトリが 1 度呼ばれる" {
                // execute
                sut.execute(UploadYesterdayParam(listOf("20240704"), listOf(byteArrayOf())))

                // assert
                verify(photoRepository, times(1)).saveForYesterday(any(), any())
            }
        }
    }
}
