package org.yywang.test;

import org.yywang.asyevent.base.EventHandler;

/**
 *
 */
public class ArchiveEventHandler implements EventHandler<ArchiveEventArgs> {

    @Override
    public void processHandler(ArchiveEventArgs args) {
        System.out.println("process the " + args.getId() + " thread id:" + Thread.currentThread().getId());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
