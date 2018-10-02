package my.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import my.util.JsonUtil;

import java.nio.charset.Charset;
import java.util.List;

@ChannelHandler.Sharable
public class MessageEncoder extends MessageToMessageEncoder<MessageOutput> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageOutput msg, List<Object> out) throws Exception {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer();
        writeStr(buf, msg.getRequestId());
        writeStr(buf, msg.getType());
        writeStr(buf, JsonUtil.toJson(msg.getPayload()));
        out.add(buf);
    }

    private void writeStr(ByteBuf buf, String s) {
        buf.writeInt(s.length());
        buf.writeBytes(s.getBytes(Charset.forName("utf-8")));
    }

}