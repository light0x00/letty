package io.github.light0x00.lighty.examples.utf32;

import io.github.light0x00.lighty.core.buffer.RecyclableBuffer;
import io.github.light0x00.lighty.core.handler.ChannelContext;
import io.github.light0x00.lighty.core.handler.OutboundPipeline;
import io.github.light0x00.lighty.core.handler.adapter.OutboundChannelHandlerAdapter;
import io.github.light0x00.lighty.core.util.Tool;
import lombok.extern.slf4j.Slf4j;

/**
 * @author light0x00
 * @since 2023/7/4
 */
@Slf4j
public class UTF32Encoder extends OutboundChannelHandlerAdapter {

    @Override
    public void onWrite(ChannelContext context, Object msg, OutboundPipeline next) {
        log.info("encode..");

        String str = ((String) msg);

        int capacity = str.codePointCount(0, str.length()) * 4;

        RecyclableBuffer buf = context.allocateBuffer(capacity);

        str.codePoints().forEach(buf::putInt);

        context.channel().write(buf)
                .addListener(
                        f -> {
                            if (f.isSuccess()) {
                                log.info("actual write");
                            } else {
                                f.cause().printStackTrace();
                            }
                        }
                );

        next.invoke(Tool.intToBytes("\n".codePointAt(0)))
                .addListener(
                        f -> {
                            log.info("write result:{}", f.isSuccess());
                        }
                ) ;
    }

}
