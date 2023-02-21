package com.goofy.cache.event

import com.fasterxml.jackson.module.kotlin.readValue
import com.goofy.cache.mapper
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Component

@Component
class RedisPublisher(
    private val redisTemplate: RedisTemplate<String, Any>
) {
    fun publish(channel: ChannelTopic, message: Any) {
        redisTemplate.convertAndSend(channel.topic, message)
    }
}

@Component
class RedisSubscriber(
    private val redisTemplate: RedisTemplate<String, Any>
) : MessageListener {
    override fun onMessage(message: Message, pattern: ByteArray?) {
        val channel = redisTemplate.getChannel(message)
        val content = redisTemplate.getMessage(message)

        /** 추후 subscribe 로직 구현 */
        println("channel : $channel")
        println("conent : $content")
    }
}

data class EventModel(
    val id: Long,
    val description: String
)

fun RedisTemplate<String, Any>.getChannel(message: Message): String {
    return this.stringSerializer.deserialize(message.channel)
        ?: throw RedisPubSubException("channel is null or empty")
}

fun RedisTemplate<String, Any>.getMessage(message: Message): EventModel {
    val messageBody = this.stringSerializer.deserialize(message.body)
        ?: throw RedisPubSubException("message is null or empty")

    return mapper.readValue(messageBody)
}

class RedisPubSubException(
    override val message: String
) : RuntimeException(message)
