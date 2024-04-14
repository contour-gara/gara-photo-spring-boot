package org.contourgara.garaphotospringboot.infrastructure

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TokenMapper {
  // TODO: すでにレコードがある場合の例外処理
  @Insert("INSERT INTO token (id, access_token, refresh_token, date_time) VALUES (1, #{accessToken}, #{refreshToken}, #{dateTime})")
  fun insert(tokenEntity: TokenEntity)
}
