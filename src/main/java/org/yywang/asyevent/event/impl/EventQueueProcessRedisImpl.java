package org.yywang.asyevent.event.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.yywang.asyevent.base.EventArgs;
import org.yywang.asyevent.base.EventHandler;
import org.yywang.asyevent.event.EventQueueProcess;
import org.yywang.asyevent.topic.Topic;
import org.yywang.asyevent.topic.TopicManager;
import org.yywang.test.ArchiveEventArgs;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 事件队列处理
 */
@Service("eventQueueProcess")
public class EventQueueProcessRedisImpl implements EventQueueProcess {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    @Resource
    private Integer maxExecutorTime;

    private boolean isRunning = false;

    private List<byte[]> rowKeys = new ArrayList<byte[]>();

    @Override
    public void start() {
        initRowKeys();

        isRunning = true;
        popRedisEvents();
    }

    private void initRowKeys() {
        Topic[] topics = TopicManager.Instance.getTopics();
        for (Topic topic : topics) {
            rowKeys.add(redisTemplate.getKeySerializer().serialize(topic.getTopicId()));
        }
    }

    private void popRedisEvents() {
        while (isRunning) {
            try {
                redisTemplate.execute(new RedisCallback() {
                    @Override
                    public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                        List<byte[]> lists = redisConnection.bRPop(1000, rowKeys.toArray(new byte[rowKeys.size()][]));
                        if (CollectionUtils.isEmpty(lists)) {
                            return null;
                        }
                        final String topicId = (String) redisTemplate.getKeySerializer().deserialize(lists.get(0));
                        final EventArgs eventArgs = (EventArgs) redisTemplate.getValueSerializer().deserialize(lists.get(1));
                        if (!isBusy()) {
                            taskExecutor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    process(topicId, eventArgs);
                                }
                            });
                        }
                        return null;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private synchronized boolean isBusy() {
        if (taskExecutor.getActiveCount() >= taskExecutor.getMaxPoolSize()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return isBusy();
        } else {
            return false;
        }
    }

    private void process(String topicId, final EventArgs eventArgs) {
        final Topic topic = TopicManager.Instance.getTopic(topicId);
        try {
            if (!isBusy()) {
                taskExecutor.submit(new Runnable() {
                    @Override
                    public void run() {
                        process(eventArgs, topic.getEventHandlers());
                    }
                }).get(maxExecutorTime, TimeUnit.SECONDS);
            }
            //异常处理，需要采用某种机制考虑，重试？加入异常队列？
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    private void process(EventArgs eventArgs, List<EventHandler> eventHandlers) {
        //按顺序处理EventHander
        for (EventHandler eventHandler : eventHandlers) {
            eventHandler.processHandler(eventArgs);
        }
    }
}
