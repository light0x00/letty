package io.github.light0x00.lighty.core.handler;

/**
 * @author light0x00
 * @since 2023/7/4
 */
public interface InboundPipeline {

    //TODO 返回 future
    void next(Object data);

}
