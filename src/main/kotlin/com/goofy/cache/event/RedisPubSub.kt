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
    /** 특정 채널에 Message 발행 */
    fun publish(channel: String, message: Any) {
        redisTemplate.convertAndSend(channel, message)
    }

    /** 특정 채널에 Message 발행 */
    fun publish(channel: ChannelTopic, message: Any) {
        redisTemplate.convertAndSend(channel.topic, message)
    }
}

@Component
class RedisSubscriber(
    private val redisTemplate: RedisTemplate<String, Any>
) : MessageListener {
    /** 특정 채널의 메세지 구독 */
    override fun onMessage(message: Message, pattern: ByteArray?) {
        val channel = redisTemplate.getChannel(message)
        val content = redisTemplate.getMessage(message)

        run {
            // TODO : 별도의 로직을 수행한다.
        }
    }
}

/** 발행 이벤트 메세지 */
data class EventModel(
    val id: Long,
    val description: String
)

/** 채널 정보 조회 */
fun RedisTemplate<String, Any>.getChannel(message: Message): String {
    return this.stringSerializer.deserialize(message.channel)
        ?: throw RedisPubSubException("channel is null or empty")
}

/** 메세지 조회 */
fun RedisTemplate<String, Any>.getMessage(message: Message): EventModel {
    val messageBody = this.stringSerializer.deserialize(message.body)
        ?: throw RedisPubSubException("message is null or empty")

    return mapper.readValue(messageBody)
}

class RedisPubSubException(
    override val message: String
) : RuntimeException(message)
