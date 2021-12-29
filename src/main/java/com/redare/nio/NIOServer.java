package com.redare.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author 田昆
 * @date 2021/12/27 16:02
 **/
public class NIOServer {
    public static void main(String[] args) throws IOException {
        //创建serverSocket->ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //得到一个Selector 对象
        Selector selector = Selector.open();
        //绑定一个端口6666，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置喂非阻塞
        serverSocketChannel.configureBlocking(false);
        //把serverSocketChannel注册到 selector 关心事件喂OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端链接
        while (true) {
            //等待一秒，如果没有事件发生，返回
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了一秒，无链接");
                continue;
            }
            //如果返回的>0
            //selector.selectedKeys返回关注事件的集合
            Set<SelectionKey> keys = selector.selectedKeys();
            //遍历
            Iterator<SelectionKey> keyIterator = keys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //将SocketChannel注册到selector,关注事件为OP_READ,同时给socketChannel关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    if(key.isReadable()) {
                        //通过key方向获取对于的channel

                        //将SocketChannel设置为非阻塞
                        SocketChannel channel = (SocketChannel)key.channel();
                        channel.configureBlocking(false);
                        //获取到该channel关联的buffer
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        channel.read(buffer);
                        System.out.println("from 客户端"+new String(buffer.array()));
                    }
                    //手动从集合中移动当前的selectionKey,防止重复操作
                    keyIterator.remove();
                }
            }

        }
    }
}
