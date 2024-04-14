package org.contourgara.garaphotospringboot.infrastructure

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

@Mapper
interface TokenMapper {
  @Insert("INSERT INTO token (id, access_token, refresh_token, date_time) VALUES (1, #{accessToken}, #{refreshToken}, #{dateTime})")
  fun insert(tokenEntity: TokenEntity)

  @Select("SELECT access_token, refresh_token, date_time FROM token WHERE id = 1")
  fun find(): TokenEntity?

  @Update("UPDATE token SET access_token = #{accessToken}, refresh_token = #{refreshToken}, date_time = #{dateTime} WHERE id = 1")
  fun update(tokenEntity: TokenEntity)
}
