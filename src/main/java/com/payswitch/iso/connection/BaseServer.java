package com.payswitch.iso.connection;

import com.payswitch.iso.AbstractIso8583Connector;
import com.payswitch.iso.handler.IsoChannelInitializer;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.MessageFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class BaseServer<T extends IsoMessage> extends AbstractIso8583Connector<ServerConfiguration, ServerBootstrap, T> {

    public BaseServer(int port, ServerConfiguration config, MessageFactory<T> messageFactory) {
        super(config, messageFactory);
        setSocketAddress(new InetSocketAddress(port));
    }

    public BaseServer(int port, MessageFactory<T> messageFactory) {
        this(port, ServerConfiguration.newBuilder().build(), messageFactory);
    }

    public void start() throws InterruptedException {

        getBootstrap().bind().addListener(
                (ChannelFuture future) -> {
                    final Channel channel = future.channel();
                    setChannel(channel);
                    logger.info("Server is started and listening at {}", channel.localAddress());
                }
        ).sync().await();
    }

    @Override
    protected ServerBootstrap createBootstrap() {

        final ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(getBossEventLoopGroup(), getWorkerEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .localAddress(getSocketAddress())
                .childHandler(new IsoChannelInitializer<>(
                        getConfiguration(),
                        getConfigurer(),
                        getWorkerEventLoopGroup(),
                        getIsoMessageFactory(),
                        getMessageHandler()
                ));

        configureBootstrap(bootstrap);

        bootstrap.validate();

        return bootstrap;
    }

    public void shutdown() {
        stop();
        super.shutdown();
    }

    /**
     * @return True if server is ready to accept connections.
     */
    public boolean isStarted() {
        final Channel channel = getChannel();
        return channel != null && channel.isOpen();
    }

    @SuppressWarnings("WeakerAccess")
    public void stop() {
        final Channel channel = getChannel();
        if (channel == null) {
            throw new IllegalStateException("Server is not started.");
        }
        logger.info("Stopping the Server...");
        try {
            channel.deregister();
            channel.close().sync().await(10, TimeUnit.SECONDS);
            logger.info("Server was Stopped.");
        } catch (InterruptedException e) {
            logger.error("Error while stopping the server", e);
        }

    }

    /**
     * Sends asynchronously and returns a {@link ChannelFuture}
     *
     * @param isoMessage A message to send
     * @return ChannelFuture which will be notified when message is sent
     */
    public ChannelFuture sendAsync(IsoMessage isoMessage) {
        Channel channel = getChannel();
        if (channel == null) {
            throw new IllegalStateException("Channel is not opened");
        }
        if (!channel.isWritable()) {
            throw new IllegalStateException("Channel is not writable");
        }
        return channel.writeAndFlush(isoMessage);
    }

    public void send(IsoMessage isoMessage) throws InterruptedException {
        sendAsync(isoMessage).sync().await();
    }

    public void send(IsoMessage isoMessage, long timeout, TimeUnit timeUnit) throws InterruptedException {
        sendAsync(isoMessage).sync().await(timeout, timeUnit);
    }
    
}
