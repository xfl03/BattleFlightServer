package battleflight.server.websocket;

import battleflight.server.pool.MainPool;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class WebSocketInitializer extends ChannelInitializer<SocketChannel> {
	private MainPool mainPool;
	public WebSocketInitializer(MainPool mainPool){
		this.mainPool=mainPool;
	}

	@Override
	protected void initChannel(SocketChannel arg0) throws Exception {
		ChannelPipeline pipeline = arg0.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new WebSocketHandler(mainPool));
	}

}
