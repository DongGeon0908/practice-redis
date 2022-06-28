package com.goofy.cache.controller

import com.goofy.cache.cache.board.BoardCacheService
import com.goofy.cache.noContent
import com.goofy.cache.service.BoardService
import com.goofy.cache.wrapDataResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/cache/v1/boards")
class BoardController(
    private val boardService: BoardService,
    private val boardCacheService: BoardCacheService
) {
    @PostMapping
    fun insert() = boardService.insert().noContent()

    @GetMapping("/cache/{type}")
    fun cache(@PathVariable type: Long) = boardService.get(type).wrapDataResponse()

    @GetMapping("/nocache/{type}")
    fun noCache(@PathVariable type: Long) = boardService.getNoCahce(type).wrapDataResponse()

    @PostMapping("/evict/{type}")
    fun evict(@PathVariable type: Long) = boardCacheService.evict(type).noContent()
}
