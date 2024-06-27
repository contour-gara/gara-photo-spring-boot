package org.contourgara.garaphotospringboot.application.usecase

import org.contourgara.garaphotospringboot.application.param.UploadYesterdayParam
import org.contourgara.garaphotospringboot.domain.infrastructure.PhotoRepository
import org.springframework.stereotype.Service

@Service
class UploadYesterdayUseCase(private val photoRepository: PhotoRepository) {
  fun execute(uploadYesterdayParam: UploadYesterdayParam) {
    photoRepository.saveForYesterday(uploadYesterdayParam.convertToModel(), "/opt/photo/yesterday")
  }
}
