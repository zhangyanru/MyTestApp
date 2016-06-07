package com.example.myapp.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by yanru.zhang on 16/6/4.
 * Email:yanru.zhang@renren-inc.com
 */
public class MyServer {

    //定义ServerSocket的端口号

    private static final int SOCKET_PORT = 7000;

    public void initMyServer() {
        try {
            //创建一个ServerSocket，用于监听客户端Socket的连接请求
            ServerSocket serverSocket = new ServerSocket(SOCKET_PORT);
            //每当接收到客户端的Socket请求，服务器端也相应的创建一个Socket
            while (true) {
                System.out.println("新的客户端连接");
                //处理客户端请求
                new Thread(new ServerThread(serverSocket.accept())).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MyServer myServer = new MyServer();
        myServer.initMyServer();
    }

    public class ServerThread implements Runnable {

        //定义当前线程所处理的Socket
        private Socket clientSocket = null;
        private BufferedWriter bufferedWriter = null;


        public ServerThread(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        }


        public void run() {
            System.out.println("run...");
            try {
                if(!clientSocket.isClosed()){
                    //向客户端发消息
                    String answer = "hi client!" + clientSocket.getLocalPort();
                    System.out.println("send to client:" + answer);
                    bufferedWriter.write(answer);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }else{
                    System.out.println("socket is closed!");
                }
            } catch (SocketException e){
                e.printStackTrace();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
