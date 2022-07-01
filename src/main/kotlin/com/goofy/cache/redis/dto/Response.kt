package com.goofy.cache.redis.dto

import com.goofy.cache.redis.domain.Board

data class BoardResponse(
    val id: Long,
    val title: String,
    val content: String,
    val type: Long
) {
    constructor(board: Board) : this(
        id = board.id,
        title = board.title,
        content = board.content,
        type = board.type
    )
}
