package com.goofy.cache

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.core.env.Environment
import java.util.*

@SpringBootApplication
class PraticeRedisApplication(
    private val environment: Environment
) : ApplicationListener<ApplicationEvent> {
    private val logger = KotlinLogging.logger {}

    override fun onApplicationEvent(event: ApplicationEvent) {
        logger.info("Spring Server Profiles Status : {}", environment.activeProfiles)
    }
}

fun main(args: Array<String>) {
    timeZone()
    runApplication<PraticeRedisApplication>(*args)
}

private fun timeZone() {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
}
