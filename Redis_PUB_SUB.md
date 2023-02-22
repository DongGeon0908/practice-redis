# Redis Pub Sub이란

> Pub-Sub 흔히 채팅 혹은 알림을 보낼 때 사용하는 아키텍쳐이다. Redis도 Pub-Sub을 지원한다. 생각보다 간단한 구조로 PUB/SUB을 지원하기 때문에 충분히 이용할 가치가 있다.

### PUB/SUB이란?

pub/sub은 기본적으로 채널, 발행, 구독 3가지 키워드로 운영된다. 발행자는 특정 채널에 이벤트를 발행한다. 그러면, 해당 채널을 구독하고 있는 수신자가 해당 이벤트를 받는다. 메시지큐 시스템의 일환으로
발행자는 이벤트를 발행한 이후에 별도의 액션을 취할 필요 없이 다른 작업을 수행할 수 있다는 장점이 있다. (비동기적으로 서비스를 운영하기에 편리) 대부분의 메세지큐 서비스들이 위와 같은 PUB/SUB을 지원한다.

### Redis로 실습하기

**이벤트 발행하기**

```
기본적인 이벤트 발행 명령어
> PUBLISH [CHANNEL] [MESSAGE]

ex > PUBLISH goofy-chat hello
```

**이벤트 구독하기**

```
기본적인 이벤트 구독 명령어
> SUBSCRIBE [CHANNEL..]
ex > SUBSCRIBE goofy-chat (하나의 채널 구독)
ex > SUBSCRIBE goofy-chat goofy-chat1 (여러 채널 구독) 


특정 패턴을 가진 채널 구독 명령어
> PSUBSCRIBE [PATTERN..]
ex > PSUBSCRIBE goofy* (goofy로 시작하는 모든 채널 구독)


특정 샤드채널에 대한 구독 명령어
> SSUBSCRIBE [SHARD_CHANNEL..]
ex > SSUBSCRIBE goofy-chat (하나의 채널 구독)
ex > SSUBSCRIBE goofy-chat goofy-chat1 (여러 채널 구독) 
```

**구독 취소하기**

```
기본적인 구독 취소 명령어
> UNSUBSCRIBE [CHANNEL..]
ex > UNSUBSCRIBE goofy-chat (하나의 채널 구독 취소)
ex > UNSUBSCRIBE goofy-chat goofy-chat1 (여러 채널 구독 취소) 

특정 샤드채널에 대한 구독 취소 명령어
> SUNSUBSCRIBE [SHARD_CHANNEL..]
ex > SUNSUBSCRIBE goofy-chat (하나의 채널 구독 취소)
ex > SUNSUBSCRIBE goofy-chat goofy-chat1 (여러 채널 구독 취소) 

모든 샤드채널에 대한 구독 취소 명령어
> SUNSUBSCRIBE
ex > SUNSUBSCRIBE

특정 패턴을 가진 채널 구독 취소 명령어
> PUNSUBSCRIBE [PATTERN..]
ex > PUNSUBSCRIBE goofy* (goofy로 시작하는 모든 채널 구독)
```

**서버 상태 확인**

1. 연결이 아직 살아있는지 테스트합니다.
2. 데이터를 제공하는 서버의 기능 확인 - 그렇지 않은 경우 오류가 반환됩니다(예: 지속성에서 로드하거나 오래된 복제본에 액세스하는 동안).
3. 대기 시간 측정.

```
기본적인 서버 상태 확인 명령어
> PING
> PING (반환값으로 PONG)

메세지 기반 서버 상태 확인 명령어
> PING [MESSAGE]
> PING GOOFy (반환값으로 GOOFY)
```

**간단한 Spring-Redis-PUB/SUB MODEL**

```kotlin
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
```

### Reference

- [Redis Pub/Sub Docs](https://redis.io/docs/manual/pubsub/)
- [REPO](https://github.com/DongGeon0908/pratice-redis)
