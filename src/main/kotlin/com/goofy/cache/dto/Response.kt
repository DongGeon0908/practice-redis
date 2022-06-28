package com.goofy.cache.dto

import com.goofy.cache.domain.Board

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
