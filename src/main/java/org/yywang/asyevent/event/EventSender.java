package org.yywang.asyevent.event;

import org.yywang.asyevent.base.EventArgs;

/**
 * 事件发送
 */
public interface EventSender {

    void send(String topicId, EventArgs eventArgs);
}
