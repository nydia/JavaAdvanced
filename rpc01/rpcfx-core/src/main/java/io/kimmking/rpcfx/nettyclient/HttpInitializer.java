package io.kimmking.rpcfx.nettyclient;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.cors.CorsConfig;
import io.netty.handler.codec.http.cors.CorsConfigBuilder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpInitializer extends ChannelInitializer<Channel> {

	private SimpleChannelInboundHandler simpleChannelInboundHandler;

	public HttpInitializer(SimpleChannelInboundHandler simpleChannelInboundHandler)throws Exception{
		this.simpleChannelInboundHandler=simpleChannelInboundHandler;
	}

	@Override
	public void initChannel(Channel channel) throws Exception {
		ChannelPipeline pipeline = channel.pipeline();
		CorsConfig corsConfig = CorsConfigBuilder.forAnyOrigin().allowNullOrigin().allowCredentials()
				.build();
		//客户端模式
		pipeline.addLast("codec", new HttpClientCodec());
		pipeline.addLast("inflater",new HttpContentDecompressor());
		//大文件传输
		pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());
		pipeline.addLast("handler",simpleChannelInboundHandler.getClass().newInstance());
	}

}
