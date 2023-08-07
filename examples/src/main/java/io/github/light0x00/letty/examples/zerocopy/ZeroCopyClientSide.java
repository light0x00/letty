package io.github.light0x00.letty.examples.zerocopy;

import io.github.light0x00.letty.core.ClientBootstrap;
import io.github.light0x00.letty.core.DefaultLettyProperties;
import io.github.light0x00.letty.core.concurrent.ListenableFutureTask;
import io.github.light0x00.letty.core.eventloop.NioEventLoopGroup;
import io.github.light0x00.letty.core.facade.ChannelInitializer;
import io.github.light0x00.letty.core.facade.InitializingSocketChannel;
import io.github.light0x00.letty.core.handler.NioSocketChannel;
import io.github.light0x00.letty.examples.IdentifierThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;

/**
 * @author light0x00
 * @since 2023/8/7
 */
@Slf4j
public class ZeroCopyClientSide {

    public static void main(String[] args) {

        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(2, new IdentifierThreadFactory("client"));

        ListenableFutureTask<NioSocketChannel> connect = new ClientBootstrap()
                .group(eventLoopGroup)
                .properties(new DefaultLettyProperties() {

                    @Override
                    public int bufferPoolMaxSize() {
                        return 1024 * 1024*2;
                    }

                    @Override
                    public int readBufSize() {
                        return 131072; //19ms~26ms
//                        return 131072*2; //19ms
//                        return 131072*4; //17ms
//                        return 131072*8; //21~23ms
                    }

                })
                .channelInitializer(new ChannelInitializer() {
                    @Override
                    public void initChannel(InitializingSocketChannel channel) {
                        log.info("socket receive buffer:{}", channel.getOption(StandardSocketOptions.SO_RCVBUF));
                        channel.pipeline().add(new FileReceiver());
                    }
                })
                .connect(new InetSocketAddress(9000));


        if (connect.isSuccess()) {
            NioSocketChannel channel = connect.get();
            channel.closeFuture().sync();
        } else {
            connect.cause().printStackTrace();
        }
        eventLoopGroup.shutdown();
    }

}
