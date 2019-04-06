package com.payswitch.iso.codec;


import com.payswitch.config.ParamsConfig;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.MessageFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.IOException;

import java.text.ParseException;
import java.util.List;

public class IsoDecoder extends ByteToMessageDecoder {

    private final MessageFactory messageFactory;

    public IsoDecoder(MessageFactory messageFactory){
        this.messageFactory = messageFactory;
//        this.messageFactory = ConfigParser.createFromClasspathConfig("flexcube_config.xml");
    }
    
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List out) throws Exception {

        if (!byteBuf.isReadable()) {
            return;
        }
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        final IsoMessage isoMessage = messageFactory.parseMessage(bytes, ParamsConfig.MESSAGE_HEADER_LENGTH);
        if (isoMessage != null) {
            out.add(isoMessage);
        } else {
            throw new ParseException("Can't parse ISO8583 message", 0);
        }
    }
}
