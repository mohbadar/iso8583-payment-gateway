package com.payswitch.iso.codec;

import com.solab.iso8583.IsoMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.ByteBuffer;

@ChannelHandler.Sharable
public class IsoEncoder extends MessageToByteEncoder<IsoMessage> {

    private final int lengthHeaderLength;

    public IsoEncoder(int lengthHeaderLength) {
        this.lengthHeaderLength = lengthHeaderLength;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, IsoMessage isoMessage, ByteBuf out) {
        if (lengthHeaderLength == 0) {
            byte[] bytes = isoMessage.writeData();
            out.writeBytes(bytes);
        } else {
            ByteBuffer byteBuffer = isoMessage.writeToBuffer(lengthHeaderLength);
            out.writeBytes(byteBuffer);
        }
    }
}
