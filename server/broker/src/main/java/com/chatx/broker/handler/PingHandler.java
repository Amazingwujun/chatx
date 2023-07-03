package com.chatx.broker.handler;

import com.chatx.broker.constants.ChatxMessageType;
import com.chatx.broker.entity.ChatxMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author Jun
 * @since 1.0.0
 */
@Handler(type = ChatxMessageType.PING)
public class PingHandler implements ChatxMessageHandler {

    @Override
    public void process(ChannelHandlerContext ctx, ChatxMessage msg) {
        ctx.writeAndFlush(ChatxMessage.PONG);
    }
}
