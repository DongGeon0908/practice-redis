package com.goofy.cache.service

import com.goofy.cache.domain.Board
import com.goofy.cache.dto.BoardResponse
import com.goofy.cache.repository.BoardRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
    private val boardRepository: BoardRepository
) {
    @Transactional
    fun insert() {
        val boards1 = (1L..1000L).map {
            Board(
                title = "title$it",
                content = "content$it",
                type = 1L
            )
        }

        val boards2 = (1L..1000L).map {
            Board(
                title = "title$it",
                content = "content$it",
                type = 2L
            )
        }

        boardRepository.saveAll(boards1)
        boardRepository.saveAll(boards2)
    }

    @Cacheable(
        cacheManager = "cacheManager",
        value = ["cache::boards"],
        key = "#type"
    )
    @Transactional(readOnly = true)
    fun get(type: Long) = boardRepository.findAllByType(type).map { BoardResponse(it) }

    @Transactional(readOnly = true)
    fun getNoCahce(type: Long) = boardRepository.findAllByType(type).map { BoardResponse(it) }
}
