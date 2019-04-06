package com.payswitch.iso.handler;

import com.payswitch.iso.ConnectorConfiguration;
import com.payswitch.iso.ConnectorConfigurer;
import com.payswitch.iso.codec.IsoDecoder;
import com.payswitch.iso.codec.IsoEncoder;
import com.solab.iso8583.MessageFactory;
import io.netty.bootstrap.AbstractBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.IdleStateHandler;

@SuppressWarnings("WeakerAccess")
public class IsoChannelInitializer<
        T extends Channel, B extends AbstractBootstrap, C extends ConnectorConfiguration> extends ChannelInitializer<T> {

    private final C configuration;
    private final ConnectorConfigurer<C, B> configurer;
    private final EventLoopGroup workerGroup;
    private final MessageFactory isoMessageFactory;
    private final ChannelHandler[] customChannelHandlers;
    private final IsoEncoder isoMessageEncoder;
    private final ChannelHandler loggingHandler;
    private final ChannelHandler parseExceptionHandler;

    public IsoChannelInitializer(
            C configuration,
            ConnectorConfigurer<C, B> configurer,
            EventLoopGroup workerGroup,
            MessageFactory isoMessageFactory,
            ChannelHandler... customChannelHandlers) {
        this.configuration = configuration;
        this.configurer = configurer;
        this.workerGroup = workerGroup;
        this.isoMessageFactory = isoMessageFactory;
        this.customChannelHandlers = customChannelHandlers;

        this.isoMessageEncoder = createIso8583Encoder(configuration);
        this.loggingHandler = createLoggingHandler(configuration);
        this.parseExceptionHandler = createParseExceptionHandler();
    }

    @Override
    public void initChannel(T ch) {
        final ChannelPipeline pipeline = ch.pipeline();

        final int lengthFieldLength = configuration.getFrameLengthFieldLength();
        pipeline.addLast("lengthFieldFameDecoder",
                new LengthFieldBasedFrameDecoder(
                        configuration.getMaxFrameLength(), configuration.getFrameLengthFieldOffset(), lengthFieldLength,
                        configuration.getFrameLengthFieldAdjust(), lengthFieldLength));
        pipeline.addLast("iso8583Decoder", createIso8583Decoder(isoMessageFactory));

        pipeline.addLast("iso8583Encoder", isoMessageEncoder);

        if (configuration.addLoggingHandler()) {
            pipeline.addLast(workerGroup, "logging", loggingHandler);
        }

        if (configuration.replyOnError()) {
            pipeline.addLast(workerGroup, "replyOnError", parseExceptionHandler);
        }

        pipeline.addLast("idleState", new IdleStateHandler(0, 0, configuration.getIdleTimeout()));
        pipeline.addLast("idleEventHandler", new IdleEventHandler(isoMessageFactory));
        if (customChannelHandlers != null) {
            pipeline.addLast(workerGroup, customChannelHandlers);
        }

        if (configurer != null) {
            configurer.configurePipeline(pipeline, configuration);
        }
    }

    protected MessageFactory getIsoMessageFactory() {
        return isoMessageFactory;
    }

    protected ChannelHandler createParseExceptionHandler() {
        return new ParseExceptionHandler(isoMessageFactory, true);
    }

    protected IsoEncoder createIso8583Encoder(C configuration) {
        return new IsoEncoder(configuration.getFrameLengthFieldLength());
    }

    protected IsoDecoder createIso8583Decoder(final MessageFactory messageFactory) {
        try {
            return new IsoDecoder(messageFactory);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected ChannelHandler createLoggingHandler(C configuration) {
//        IsoMessageLoggingHandler handler = new IsoMessageLoggingHandler(LogLevel.DEBUG,
//                configuration.logSensitiveData(),
//                configuration.logFieldDescription(),
//                configuration.getSensitiveDataFields());
        return null;
    }

}
