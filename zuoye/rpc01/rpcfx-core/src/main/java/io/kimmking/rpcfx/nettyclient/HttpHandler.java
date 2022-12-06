package io.kimmking.rpcfx.nettyclient;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.apache.commons.lang.StringUtils;

public class HttpHandler extends SimpleChannelInboundHandler<HttpObject> {

    private ChannelPromise channelPromise;

    private StringBuffer jsonBuilder = new StringBuffer();

    private StringBuffer failBuilder = new StringBuffer();

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) {
        try {
            HttpContent chunk = (HttpContent) httpObject;
            if (chunk instanceof LastHttpContent) {
                ByteBuf content = chunk.content();
                String json = content.toString(CharsetUtil.UTF_8);
                jsonBuilder.append(json);
                channelPromise.setSuccess();
            } else {
                ByteBuf content = chunk.content();
                String json = content.toString(CharsetUtil.UTF_8);
                jsonBuilder.append(json);
            }
        } catch(Exception e) {
            e.printStackTrace();
            String message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
            failBuilder.append(message);
        } finally {
            ReferenceCountUtil.release(httpObject);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public static Object call(HttpRequest request,Channel channel) throws Exception {
        HttpHandler handler = (HttpHandler) channel.pipeline().get("handler");
        ChannelPromise channelPromise = channel.newPromise();
        handler.setChannelPromise(channelPromise);
        channel.write(request);
        channel.flush();
        channelPromise.await();
        String failResult = handler.getFailResult();
        String result = handler.getResult();
        if (!StringUtils.isNotBlank(failResult)) {
            throw new RuntimeException(failResult);
        }
        return result;
    }

    public void setChannelPromise(ChannelPromise channelPromise) {
        this.channelPromise = channelPromise;
    }

    public String getResult() {
        return jsonBuilder.toString();
    }

    public String getFailResult() {
        return failBuilder.toString();
    }

}
