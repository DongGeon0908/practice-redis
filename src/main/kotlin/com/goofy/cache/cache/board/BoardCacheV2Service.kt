package com.goofy.cache.cache.board

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

/**
 * Redis 예제용 Cache Service
 **/
@Service
class BoardCacheV2Service(
    private val redis: RedisTemplate<String, Any>
) {
    enum class BoardCacheV2Key(val key: String) {
        CACHE_EXAMPLE("cache::example"),
        ;

        fun key(type: Long) = """${this.key}::${type}"""
    }

    /**
     * value를 1씩 증가시킴
     **/
    fun increment(type: Long) = redis.opsForValue().increment(BoardCacheV2Key.CACHE_EXAMPLE.key(type))

    /**
     * value를 1씩 감소시킴
     **/
    fun decrease(type: Long) = redis.opsForValue().decrement(BoardCacheV2Key.CACHE_EXAMPLE.key(type))
}
