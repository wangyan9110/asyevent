package org.yywang.asyevent.topic;

import org.yywang.asyevent.base.EventHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 主题
 */
public class Topic {

    private String topicId;

    private Boolean isEnable;

    private String topicDes;

    private List<EventHandler> eventHandlers = new ArrayList<EventHandler>();

    public Topic(String topicId, String topicDes, List<EventHandler> eventHandlers, Boolean isEnable) {
        this.topicId = topicId;
        this.topicDes = topicDes;
        this.eventHandlers = eventHandlers;
        this.isEnable = isEnable;
    }

    public Topic(String topicId, String topicDes, List<EventHandler> eventHandlers) {
        this(topicId, topicDes, eventHandlers, true);
    }

    public Topic(String topicId, Boolean isEnable) {
        this(topicId, topicId, new ArrayList<EventHandler>(), isEnable);
    }

    public Topic(String topicId,EventHandler eventHandler){
        this(topicId, topicId, new ArrayList<EventHandler>(), true);
        this.eventHandlers.add(eventHandler);
    }

    public Topic(String topicId) {
        this(topicId, topicId, new ArrayList<EventHandler>());
    }

    public void addEventHander(EventHandler eventHandler) {
        eventHandlers.add(eventHandler);
    }

    public void removeEventHander(EventHandler eventHandler) {
        eventHandlers.remove(eventHandler);
    }

    public void clearEventHanders() {
        eventHandlers.clear();
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public String getTopicDes() {
        return topicDes;
    }

    public void setTopicDes(String topicDes) {
        this.topicDes = topicDes;
    }


    public List<EventHandler> getEventHandlers() {
        return eventHandlers;
    }

    public void setEventHandlers(List<EventHandler> eventHandlers) {
        this.eventHandlers = eventHandlers;
    }
}
