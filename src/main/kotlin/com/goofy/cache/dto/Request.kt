package com.goofy.cache.dto

data class RedisBoardRequest(
    val id: Long,
    val title: String,
    val content: String
)
