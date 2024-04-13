package org.contourgara.garaphotospringboot.infrastructure

import org.apache.ibatis.annotations.Mapper

@Mapper
interface TokenMapper {
  fun insert(tokenEntity: TokenEntity)
}
