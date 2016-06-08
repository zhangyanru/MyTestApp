package com.example.myapp.util;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yanru.zhang on 16/6/4.
 * Email:yanru.zhang@renren-inc.com
 */
public class MyServer {

    //定义ServerSocket的端口号

    private static final int SOCKET_PORT = 8000;

    private ExecutorService executorService = Executors.newFixedThreadPool(5);//创建一个可重用固定线程数的线程池

    private static List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    static class Server extends Thread{
        ServerSocket serverSocket = null;
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;

        @Override
        public void run() {
            super.run();

            try {
                serverSocket = new ServerSocket(SOCKET_PORT);
                System.out.print("启动服务器成功");
                while (true){
                    System.out.println("等待客户端");
                    Socket client = serverSocket.accept();
                    clients.add(client);
                    System.out.println("连接成功" + client.toString());
                    new ListenerClient(client).start();
                }
            } catch (IOException e) {
                System.out.println("启动服务器失败");
                e.printStackTrace();
            }
        }

        public synchronized void sendMsg(String msg) {
            try {
                for (int i = 0; i < clients.size(); i++) {
                    Socket client = clients.get(i);
                    printWriter = new PrintWriter(client.getOutputStream(), true);
                    printWriter.println(msg);
                }

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }


    static class ListenerClient extends Thread {
        BufferedReader reader;
//        BufferedWriter writer;
        Socket client;
        public ListenerClient( Socket client) {
            this.client=client;
        }
        //为每一个客户端创建线程等待接收信息，然后把信息广播出去
        public void run() {
            String msg = null;
            while (true) {
                try {
                    reader = new BufferedReader(new InputStreamReader(
                            client.getInputStream()));

                    msg = reader.readLine();
                    if(msg!=null){
                        sendMsg(msg);
                    }

                } catch (IOException e) {
                    println(e.toString());
                    // e.printStackTrace();
                    break;
                }
                if (msg != null && msg.trim() != "") {
                    println(">>" + msg);
                }
            }
        }
        //把信息广播到所有用户
        public synchronized void sendMsg(String msg) {
            try {
                for (int i = 0; i < clients.size(); i++) {
                    Socket client = clients.get(i);
                    if(!client.isClosed()){
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                        writer.write(msg + "\n");
                        writer.flush();
                    }else{
                        println(">>closed!");
                    }

                }

            } catch (Exception e) {
                println(e.toString());
            }
        }



        public void println(String s) {
            if (s != null) {
                System.out.println(s + "\n");
            }
        }
    }
}
