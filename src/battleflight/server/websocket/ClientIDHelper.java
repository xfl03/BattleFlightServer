package battleflight.server.websocket;

import java.net.SocketAddress;

import battleflight.server.hash.MD5Helper;
import io.netty.channel.ChannelHandlerContext;

public class ClientIDHelper {
	public static String generate(ChannelHandlerContext ctx){
		SocketAddress addr=ctx.channel().remoteAddress();
		String temp=addr.toString()+System.currentTimeMillis();
		return "CLIENT"+MD5Helper.generateShortMD5(temp);
	}
}
