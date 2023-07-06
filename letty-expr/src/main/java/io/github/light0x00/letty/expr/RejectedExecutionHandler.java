package io.github.light0x00.letty.expr;

import io.github.light0x00.letty.expr.eventloop.SingleThreadExecutor;

/**
 * Similar to {@link java.util.concurrent.RejectedExecutionHandler} but specific to {@link SingleThreadExecutor}.
 */
public interface RejectedExecutionHandler {

    /**
     * Called when someone tried to add a task to {@link SingleThreadExecutor} but this failed due capacity
     * restrictions.
     */
    void rejected(Runnable task, SingleThreadExecutor executor);
}
