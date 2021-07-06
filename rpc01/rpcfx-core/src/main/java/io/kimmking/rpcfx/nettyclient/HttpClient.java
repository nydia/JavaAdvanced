package io.kimmking.rpcfx.nettyclient;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static io.netty.buffer.Unpooled.copiedBuffer;

/**
 * netty 客户端
 */
public class HttpClient {

    private EventLoopGroup group = new NioEventLoopGroup();
    public Bootstrap b = new Bootstrap();

    public HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);

    public HttpClient() {
        try {
            b.group(group).channel(NioSocketChannel.class)
                    .handler(new HttpInitializer(new HttpHandler()));
        } catch (Exception e) {
            e.printStackTrace();
            group.shutdownGracefully();
            factory.cleanAllHttpData();
        }
    }

    /**
     * 发送json
     */
    public String doPostJson(String url, String json) {
        try {
            //链接uri
            URI uriSimple = new URI(url);
            //主机地址
            String host = uriSimple.getHost();
            //端口
            Integer port = uriSimple.getPort();
            //建立频道
            Channel channel = b.connect(host, port).sync().channel();
            //把json转为netty的bytebuf
            ByteBuf buf = copiedBuffer(json, CharsetUtil.UTF_8);
            //获取fuulhttprequest (请求头、请求body)
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,
                    HttpMethod.POST, uriSimple.toASCIIString());
            //请求body写入bytebuf
            request.content().writeBytes(buf);
            //设置请求头
            HttpHeaders headers = request.headers();
            //设置content-type为json请求
            headers.set(HttpHeaderNames.CONTENT_TYPE, "application/json");
            //需要手动指定body长度
            headers.setInt(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());

            //调用请求
            String resultjson = (String) HttpHandler.call(request, channel);
            return resultjson;

        } catch (Exception e) {
            e.printStackTrace();
            group.shutdownGracefully();
            factory.cleanAllHttpData();
            return errorResult(e);
        }
    }

    private String errorResult(Exception e){
        Map<String,String> msg = new HashMap<>();
        msg.put("msg", e.getMessage());
        return JSON.toJSONString(msg);
    }


}
