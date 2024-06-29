package org.contourgara.garaphotospringboot.presentation.request

import com.fasterxml.jackson.annotation.JsonProperty
import org.contourgara.garaphotospringboot.application.param.FetchTokenParam

data class FetchTokenRequest(
    @JsonProperty("code") val code: String,
    @JsonProperty("codeChallenge") val codeChallenge: String,
) {
    fun convertToParam(): FetchTokenParam {
        return FetchTokenParam(code, codeChallenge)
    }
}
