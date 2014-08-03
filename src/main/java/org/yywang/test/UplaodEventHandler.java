package org.yywang.test;

import org.yywang.asyevent.base.EventHandler;

/**
 * 处理上传
 */
public class UplaodEventHandler implements EventHandler<UploadEventArgs> {

    @Override
    public void processHandler(UploadEventArgs args) {
        System.out.println("id:"+ args.getId()+" the threadId"+Thread.currentThread().getId());
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
