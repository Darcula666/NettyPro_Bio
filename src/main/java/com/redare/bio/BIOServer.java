package com.redare.bio;



import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 田昆
 * @date 2021/12/24 16:53
 **/
public class BIOServer {
    public static void main(String[] args) throws IOException {
        //线程池机制
        //1、创建一个线程池
        //2、如果有客户端链接，就创建一个线程，与之通宵（单独写一个方法）
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        //创建ServerSocker
        ServerSocket serverSocker=new ServerSocket(6666);
        System.out.println("服务器启动了");
        while(true){
            final Socket socket = serverSocker.accept();
            System.out.println("链接到一个客户端");
            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    handle(socket);
                    //可以和客户端通讯
                }
            });
        }
    }
    //编写一个handle 方法，和客户端通讯
    public static void handle (Socket socket){
        byte[] bytes=new byte[1024];
        try {
            //通过socket 获取输入流
            InputStream inputStream=socket.getInputStream();
            //循环读取客户端发送的数据
            while (true) {
                int read=  inputStream.read(bytes);
                if(read !=-1){
                    System.out.println(new String(bytes,0,read));
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("关闭了和client的链接");
        }
    }
}
