package com.goofy.cache

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.goofy.cache.common.ResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

fun <T> T.wrapDataResponse() = ResponseEntity.ok().body(ResponseDto(this))
fun <T> T.noContent(): ResponseEntity<Void> = ResponseEntity(HttpStatus.NO_CONTENT)

val mapper: ObjectMapper = jacksonObjectMapper()
    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
    .registerModule(JavaTimeModule())
    .registerModule(
        KotlinModule.Builder()
            .withReflectionCacheSize(512)
            .configure(KotlinFeature.NullToEmptyCollection, false)
            .configure(KotlinFeature.NullToEmptyMap, false)
            .configure(KotlinFeature.NullIsSameAsDefault, false)
            .configure(KotlinFeature.SingletonSupport, false)
            .configure(KotlinFeature.StrictNullChecks, false)
            .build()
    )
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
