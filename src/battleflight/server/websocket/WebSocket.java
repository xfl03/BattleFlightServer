package battleflight.server.websocket;

import battleflight.server.pool.MainPool;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class WebSocket implements Runnable {
	public static final int PORT=8080;
	private MainPool mainPool;
	
	public WebSocket(MainPool mainPool){
		this.mainPool=mainPool;
	}
	public void run(){
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new WebSocketInitializer(mainPool));

            Channel ch = b.bind(PORT).sync().channel();

            System.out.println("WebSocket Server Started");

            ch.closeFuture().sync();
        } catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
	}
}
