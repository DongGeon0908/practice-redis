package com.goofy.cache.redis.common

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {
    @GetMapping
    fun health() = "Health Good!"
}
