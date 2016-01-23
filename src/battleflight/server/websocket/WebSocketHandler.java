package battleflight.server.websocket;

import battleflight.server.pool.MainPool;
import battleflight.server.websocket.subhandler.IWebSocketSubHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

public class WebSocketHandler extends SimpleChannelInboundHandler<Object> {
	public String clientID;
	public ChannelHandlerContext ctx0;
	public WebSocketServerHandshaker handshaker;
	
	private MainPool mainPool;
	public WebSocketHandler(MainPool mainPool){
		this.mainPool=mainPool;
	}
	
	@Override
	public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
	}
	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
		if (!req.getDecoderResult().isSuccess()) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://"+req.headers().get(HttpHeaders.Names.HOST) + "/ws", null, true);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
            ctx0=ctx;
            clientID="CLIENT"+mainPool.webSocketPool.clientList.size();
            mainPool.webSocketPool.clientList.put(clientID,this);
        }
	}
	private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpHeaders.setContentLength(res, res.content().readableBytes());
        }

        ctx.channel().writeAndFlush(res);
    }
	private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
		if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
        }
        
        String text = ((TextWebSocketFrame) frame).text();
        System.out.printf("%s received %s%n", ctx.channel(), text);
        String[] temp0=text.split(",,", 2);
        if (temp0.length!=2){
        	ctx.channel().write(new TextWebSocketFrame("Wrong Pattern"));
        	return;
        }
        IWebSocketSubHandler handler=mainPool.webSocketPool.handlerList.get(temp0[0]);
        if(handler==null){
        	ctx.channel().write(new TextWebSocketFrame("Target Not Found"));
        	return;
        }
        handler.handle(mainPool, temp0[1], clientID);
	}
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
	
}
