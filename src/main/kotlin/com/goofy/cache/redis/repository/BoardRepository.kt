package com.goofy.cache.redis.repository

import com.goofy.cache.redis.domain.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository : JpaRepository<Board, Long> {
    fun findAllByType(type: Long): List<Board>
}
