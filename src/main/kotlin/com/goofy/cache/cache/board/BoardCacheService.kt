package com.goofy.cache.cache.board

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class BoardCacheService(
    private val redis: RedisTemplate<String, Any>
) {
    enum class BoardCacheKey(val key: String) {
        BOARD_SELECT_CACHE_KEY("cache::boards"),
        ;

        fun key(type: Long) = """${this.key}::${type}"""
    }

    fun evict(type: Long) {
        redis.delete(BoardCacheKey.BOARD_SELECT_CACHE_KEY.key(type))
    }
}
