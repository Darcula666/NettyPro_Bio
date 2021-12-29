package com.redare.netty.simple.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author 田昆
 * @date 2021/12/28 14:55
 * 1、我们自定义一个Handler需要继承netty规定好的某个HandlerAdapter
 * 2、这时候我们的自定义Handler，才能称为一个handler
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 读取数据（我们可以读取客户端发送的消息）
     *
     * @param ctx
     * @param msg
     * @throws Exception 1、ChannelHandlerContext 上下文对象，含有管道pipeLine，通道channel，地址
     *                   2、Object msg:就是客户端发送的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        System.out.println("server ctx =" + ctx);
        //将msg转成一个ByteBuf
        //ByteBuf 是Netty提供的
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址是：" + ctx.channel().remoteAddress());
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        //writeAndFlush 将数据写入到缓存，并刷新
        //一般讲，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello，客户端~", CharsetUtil.UTF_8));
    }
    //处理异常，一般需要关闭通道

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
