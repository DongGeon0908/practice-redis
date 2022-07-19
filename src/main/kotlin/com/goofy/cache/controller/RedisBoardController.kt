package com.goofy.cache.controller

import com.goofy.cache.dto.RedisBoardRequest
import com.goofy.cache.service.redis.RedisBoardService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/cache/v1/redis-boards")
class RedisBoardController(
    private val redisBoardService: RedisBoardService
) {
    @PostMapping
    fun save(
        @RequestBody request: RedisBoardRequest
    ) {
        redisBoardService.save(request)
    }
}
