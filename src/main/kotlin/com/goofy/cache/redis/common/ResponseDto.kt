package com.goofy.cache.redis.common

import com.fasterxml.jackson.annotation.JsonProperty

data class ResponseDto<T>(
    @JsonProperty("data")
    val data: T
)
