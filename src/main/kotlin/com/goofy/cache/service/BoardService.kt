package com.goofy.cache.service

import com.goofy.cache.domain.Board
import com.goofy.cache.dto.BoardResponse
import com.goofy.cache.repository.BoardRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
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

    /**
     * 해당 데이터가 redis에 등록되게 되면 아래와 같이 key 값이 생성됨
     * key : cache::boards::1
     **/
    @Cacheable(
        cacheManager = "cacheManager",
        value = ["cache::boards"],
        key = "#type"
    )
    @Transactional(readOnly = true)
    fun get(type: Long) = boardRepository.findAllByType(type).map { BoardResponse(it) }

    @CacheEvict(
        cacheManager = "cacheManager",
        value = ["cache::boards"],
        key = "#type"
    )
    @Transactional(readOnly = true)
    fun evictWithAnnotation(type: Long) = "evict cache::boards::${type}"

    @Transactional(readOnly = true)
    fun getNoCahce(type: Long) = boardRepository.findAllByType(type).map { BoardResponse(it) }

    /**
     * condition의 상태에 따라 캐시 처리 진행
     **/
    @Cacheable(
        cacheManager = "cacheManager",
        condition = "#type == 1L",
        value = ["cache::boards"],
        key = "#type"
    )
    @Transactional(readOnly = true)
    fun getWithCondition(type: Long) = boardRepository.findAllByType(type).map { BoardResponse(it) }

    /**
     * 캐시 갱신
     **/
    @CachePut(
        cacheManager = "cacheManager",
        value = ["cache::boards"],
        key = "#type"
    )
    @Transactional(readOnly = true)
    fun getWittPut(type: Long) = boardRepository.findAllByType(type).map { BoardResponse(it) }
}
