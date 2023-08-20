package io.github.light0x00.lighty.core.handler;

/**
 * @author light0x00
 * @since 2023/7/4
 */
public abstract class DuplexChannelHandlerAdapter extends ChannelHandlerAdapter implements DuplexChannelHandler {

    @Override
    @Skip
    public void onRead(ChannelContext context, Object data, InboundPipeline pipeline) {
    }

    @Override
    @Skip
    public void onWrite(ChannelContext context, Object data, OutboundPipeline pipeline) {
    }
}
