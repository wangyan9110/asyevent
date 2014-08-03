package org.yywang.asyevent.base;

/**
 * 事件处理器
 *
 * @author yywang5
 */
public interface EventHandler<T extends EventArgs> {

    /**
     * 事件处理
     *
     * @param args 参数
     */
    void processHandler(T args);
}
