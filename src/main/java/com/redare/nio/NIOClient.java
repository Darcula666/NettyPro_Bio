package com.redare.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author 田昆
 * @date 2021/12/27 16:39
 **/
public class NIOClient {

    public static void main(String[] args) throws IOException {
  //Socketch一个网络通道
        SocketChannel socketChannel=SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        //链接服务器
        if(!socketChannel.connect(inetSocketAddress)){
         while(!socketChannel.finishConnect()){
             System.out.println("因为链接需要时间，客户端不会阻塞");
         }
        }
        //如果链接成功就发送数据
        String str="hello,尚硅谷";
        ByteBuffer byteBuffer= ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8));
        //发送数据，将buffer数据写入channel
        socketChannel.write(byteBuffer);
        System.in.read();
    }
}
