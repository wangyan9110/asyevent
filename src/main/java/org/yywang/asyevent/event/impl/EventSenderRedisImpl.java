package org.yywang.asyevent.event.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.yywang.asyevent.base.EventArgs;
import org.yywang.asyevent.event.EventSender;
import org.yywang.asyevent.topic.Topic;
import org.yywang.asyevent.topic.TopicManager;

import javax.annotation.Resource;

/**
 * 事件发送
 *
 * @author yywang
 */
@Service("eventSender")
public class EventSenderRedisImpl implements EventSender {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void send(String topicId, EventArgs eventArgs) {
        Topic topic = TopicManager.Instance.getTopic(topicId);
        if (topic != null && topic.getIsEnable()) {
            redisTemplate.opsForList().leftPush(topicId, eventArgs);
        }
    }
}
