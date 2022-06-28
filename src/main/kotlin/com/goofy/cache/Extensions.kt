package com.goofy.cache

import com.goofy.cache.common.ResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

fun <T> T.wrapDataResponse() = ResponseEntity.ok().body(ResponseDto(this))
fun <T> T.noContent(): ResponseEntity<Void> = ResponseEntity(HttpStatus.NO_CONTENT)
