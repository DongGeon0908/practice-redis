package com.goofy.cache.event

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

/**
 * Redis로 Message Queue 구축
 *
 * 단, 아래의 특정 이슈에 대해서는 고민 필요
 *
 * - Redis 클러스터링
 * - 사용된 메세지는 저장소에서 즉시 삭제가 진행됨
 * - Default로 설정된 Redis의 경우, 데이터 손실 가능성 존재
 *
 * @see org.springframework.data.redis.core.ListOperations
 * @see com.goofy.cache.config.RedisConfig.simpleStringRedisTemplate
 */
@Component
class RedisMessageQueue(
    private val simpleStringRedisTemplate: RedisTemplate<String, String>
) {
    fun leftPush(key: String, message: String) {
        simpleStringRedisTemplate.opsForList().leftPush(key, message)
    }

    fun leftPushAll(key: String, messages: List<String>) {
        simpleStringRedisTemplate.opsForList().leftPushAll(key, messages)
    }

    fun rightPop(key: String): String? {
        return simpleStringRedisTemplate.opsForList().rightPop(key)
    }

    fun rightPop(key: String, size: Long): List<String>? {
        return simpleStringRedisTemplate.opsForList().rightPop(key, size)
    }

    fun queueSize(key: String): Long {
        return simpleStringRedisTemplate.opsForList().size(key) ?: 0
    }
}
