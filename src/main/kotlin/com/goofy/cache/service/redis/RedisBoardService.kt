package com.goofy.cache.service.redis

import com.goofy.cache.domain.redis.RedisBoard
import com.goofy.cache.dto.RedisBoardRequest
import com.goofy.cache.repository.redis.RedisBoardRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RedisBoardService(
    private val redisBoardRepository: RedisBoardRepository
) {
    fun findById(id: Long): RedisBoard {
        return redisBoardRepository.findByIdOrNull(id)
            ?: throw RuntimeException("not found redis board")
    }

    fun save(request: RedisBoardRequest) {
        redisBoardRepository.save(
            RedisBoard(
                id = request.id,
                title = request.title,
                content = request.content
            )
        )
    }
}
