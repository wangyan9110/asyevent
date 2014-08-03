package org.yywang.asyevent.topic;

import org.yywang.asyevent.base.EventHandler;
import org.yywang.asyevent.exception.AsyEventException;

import java.util.HashMap;
import java.util.Map;

/**
 * 主题管理器
 */
public enum TopicManager {

    Instance;

    private Map<String, Topic> topics = new HashMap<String, Topic>();

    /**
     * @param topic 添加主题
     */
    public void addTopic(Topic topic) {
        if (topics.containsKey(topic.getTopicId())) {
            throw new AsyEventException(String.format(" %s is exited!", topic.getTopicId()));
        }
        topics.put(topic.getTopicId(), topic);
    }

    public void removeTopic(String topicId) {
        topics.remove(topicId);
    }

    public void addEventHander(String topicId, EventHandler eventHandler) {
        if (topics.containsKey(topicId)) {
            Topic topic = topics.get(topicId);
            topic.addEventHander(eventHandler);
            topics.put(topicId, topic);
        } else {
            throw new AsyEventException(String.format(" %s not exited!", topicId));
        }
    }

    public Topic getTopic(String topicId) {
        return topics.get(topicId);
    }

    public Topic[] getTopics() {
        return topics.values().toArray(new Topic[topics.size()]);
    }
}
