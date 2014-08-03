package org.yywang;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.yywang.asyevent.event.EventQueueProcess;
import org.yywang.asyevent.topic.Topic;
import org.yywang.asyevent.topic.TopicManager;
import org.yywang.test.ArchiveEventHandler;
import org.yywang.test.UplaodEventHandler;

/**
 * 测试两个客户端
 */
public class ProcessClient1 {
    public static void main(String[] args) {
        ApplicationContext apx=new ClassPathXmlApplicationContext("classpath:applicationContext_*.xml");
        TopicManager.Instance.addTopic(new Topic("archive"));
        TopicManager.Instance.addEventHander("archive", new ArchiveEventHandler());
        TopicManager.Instance.addTopic(new Topic("upload",new UplaodEventHandler()));
        EventQueueProcess eventQueueProcess=(EventQueueProcess)apx.getBean("eventQueueProcess");
        eventQueueProcess.start();
    }
}
