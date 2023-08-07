package io.github.light0x00.letty.examples.zerocopy;

import io.github.light0x00.letty.core.ServerBootstrap;
import io.github.light0x00.letty.core.eventloop.NioEventLoopGroup;
import io.github.light0x00.letty.core.facade.ChannelInitializer;
import io.github.light0x00.letty.core.facade.InitializingSocketChannel;
import io.github.light0x00.letty.examples.IdentifierThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author light0x00
 * @since 2023/8/5
 */
@Slf4j
public class ZeroCopyServerSide {

    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        }
        Path filePath = Paths.get(args[0]);

        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(2, new IdentifierThreadFactory("server"));
        new ServerBootstrap()
                .group(eventLoopGroup)
                .channelInitializer(new ChannelInitializer() {
                    @Override
                    public void initChannel(InitializingSocketChannel channel) {
                        channel.pipeline().add(
                                new FileSender(filePath)
                        );
                    }
                })
                .bind(new InetSocketAddress(9000));
    }

}
