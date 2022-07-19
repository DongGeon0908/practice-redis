package com.goofy.cache.domain.redis

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("redis::board")
class RedisBoard(
    @Id
    private var id: Long,
    private var title: String,
    private var content: String
)
