package io.github.light0x00.letty.examples.utf32;

import io.github.light0x00.letty.core.handler.ByteToMessageDecoder;
import io.github.light0x00.letty.core.buffer.RingByteBuffer;
import io.github.light0x00.letty.core.handler.ChannelContext;
import io.github.light0x00.letty.core.handler.InboundPipeline;

/**
 * @author light0x00
 * @since 2023/7/4
 */
public class UTF32Decoder extends ByteToMessageDecoder {

    StringBuilder sb = new StringBuilder();

    public UTF32Decoder() {
        super(4);
    }

    @Override
    protected void decode(ChannelContext context, RingByteBuffer data, InboundPipeline next) {
        while (data.remainingCanGet() >= 4) {
            String ch = Character.toString(data.getInt());
            if (ch.equals("\n")) {
                next.invoke(sb.toString());
            } else {
                sb.append(ch);
            }
        }
    }
}