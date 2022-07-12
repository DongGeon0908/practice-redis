package com.goofy.cache.cache.board

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneId

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


    /**
     * key-value를 생성하면서 expired time을 key에 등록
     **/
    fun setAndExpired(type: Long) {
        redis.opsForValue().set(
            BoardCacheV2Key.CACHE_EXAMPLE.key(type),
            "example value"
        )

        val expired = LocalDate.now()
            .atTime(23, 59, 59)
            .atZone(ZoneId.of("Asia/Seoul"))
            .toInstant()

        redis.expireAt(BoardCacheV2Key.CACHE_EXAMPLE.key(type), expired)
    }

    /**
     * key에 대한 expired time 확인
     **/
    fun getExpired(type: Long): Long {
        return redis.getExpire(BoardCacheV2Key.CACHE_EXAMPLE.key(type))
    }
}
