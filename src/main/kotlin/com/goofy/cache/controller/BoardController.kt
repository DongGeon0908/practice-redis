package com.goofy.cache.controller

import com.goofy.cache.service.BoardService
import org.springframework.web.bind.annotation.RestController

@RestController
class BoardController(
    private val boardService: BoardService
) {
}
