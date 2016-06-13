package com.example.myapp.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yanru.zhang on 16/6/4.
 * Email:yanru.zhang@renren-inc.com
 */
public class MyServer2 {

    //定义ServerSocket的端口号

    private static final int SOCKET_PORT = 8000;

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);//创建一个可重用固定线程数的线程池

    private static List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    static class Server extends Thread{
        ServerSocket serverSocket = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;

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
                    executorService.submit(new ListenerClient(client));
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
                    objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                    System.out.println("server sendMsg :" + msg);
                }

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }


    static class ListenerClient extends Thread {
        ObjectOutputStream objectOutputStream;
        ObjectInputStream objectInputStream;
        Socket client;
        public ListenerClient( Socket client) {
            this.client=client;
        }
        //为每一个客户端创建线程等待接收信息，然后把信息广播出去
        public void run() {
            ChatMessage clientMessage = null;
            while (true) {
                try {
                    objectInputStream = new ObjectInputStream(client.getInputStream());

                    clientMessage = (ChatMessage) objectInputStream.readObject();
                    if(clientMessage!=null){
                        sendMsg(clientMessage);
                    }

                } catch (IOException e) {
                    println(e.toString());
                    // e.printStackTrace();
                    break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (clientMessage != null) {
                    println(">>" + clientMessage.toString());
                }
            }
        }
        //把信息广播到所有用户
        public synchronized void sendMsg(ChatMessage  clientMessage) {
            try {
                for (int i = 0; i < clients.size(); i++) {
                    Socket client = clients.get(i);
                    if(!client.isClosed()){
                        objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                        objectOutputStream.writeObject(clientMessage);
                        objectOutputStream.flush();
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
