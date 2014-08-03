package org.yywang;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.yywang.asyevent.event.EventSender;
import org.yywang.asyevent.topic.Topic;
import org.yywang.asyevent.topic.TopicManager;
import org.yywang.test.ArchiveEventArgs;
import org.yywang.test.ArchiveEventHandler;
import org.yywang.test.UplaodEventHandler;
import org.yywang.test.UploadEventArgs;

/**
 * 测试App
 */
public class App {


    public static void main(String[] args) {
        ApplicationContext apx=new ClassPathXmlApplicationContext("classpath:applicationContext_*.xml");
        TopicManager.Instance.addTopic(new Topic("archive"));
        TopicManager.Instance.addEventHander("archive", new ArchiveEventHandler());
        TopicManager.Instance.addTopic(new Topic("upload",new UplaodEventHandler()));
        EventSender eventSender=(EventSender)apx.getBean("eventSender");
        for(int i=0;i<100;i++){
            ArchiveEventArgs archiveEventArgs=new ArchiveEventArgs();
            archiveEventArgs.setId("test-"+ i);
            eventSender.send("archive",archiveEventArgs);
            if(i%3==0){
                UploadEventArgs uploadEventArgs=new UploadEventArgs();
                uploadEventArgs.setId("upload-"+i);
                eventSender.send("upload",uploadEventArgs);
            }
        }

    }
}
