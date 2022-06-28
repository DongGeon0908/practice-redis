package com.goofy.cache.common

import com.fasterxml.jackson.annotation.JsonProperty

data class ResponseDto<T>(
    @JsonProperty("data")
    val data: T
)
