package org.contourgara.garaphotospringboot.infrastructure

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface TokenMapper {
  @Insert("INSERT INTO token (id, access_token, refresh_token, date_time) VALUES (1, #{accessToken}, #{refreshToken}, #{dateTime})")
  fun insert(tokenEntity: TokenEntity)

  @Select("SELECT access_token, refresh_token, date_time FROM token WHERE id = 1")
  fun find(): TokenEntity?
}
