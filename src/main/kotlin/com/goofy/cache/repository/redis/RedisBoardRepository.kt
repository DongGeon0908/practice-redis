package com.goofy.cache.repository.redis

import com.goofy.cache.domain.redis.RedisBoard
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * RedisRepository는 기본적으로 자동 생성되는 CRUD만을 지원한다.
 * 추가적인 쿼리를 만들려면 redisTemplate으로 custom해서 사용해야 한다.
 **/
@Repository
interface RedisBoardRepository : CrudRepository<RedisBoard, Long>
