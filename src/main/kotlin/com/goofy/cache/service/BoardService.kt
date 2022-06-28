package com.goofy.cache.service

import com.goofy.cache.repository.BoardRepository
import org.springframework.stereotype.Service

@Service
class BoardService(
    private val boardRepository: BoardRepository
) {

}
