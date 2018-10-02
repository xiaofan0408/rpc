package my.netty;

import io.netty.channel.ChannelHandlerContext;

public interface IMessageHandler<T> {

    void handle(ChannelHandlerContext ctx, String requestId, T message);

}
