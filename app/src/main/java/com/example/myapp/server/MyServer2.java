package com.example.myapp.server;

import com.example.myapp.Model.ChatMessage;
import com.example.myapp.Model.EmptyMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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

    private static List<ServerUser> clients = new ArrayList<ServerUser>();

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
                    ServerUser serverUser = new ServerUser(client);
                    clients.add(serverUser);
                    System.out.println("连接成功" + client.toString());
                    ListenerClient listenerClient = new ListenerClient(serverUser);
                    executorService.submit(listenerClient);
                    listenerClient.startCountAddTimerTask();
                }
            } catch (IOException e) {
                System.out.println("启动服务器失败");
                e.printStackTrace();
            }
        }

        public synchronized void sendMsg(String msg) {
            try {
                for (int i = 0; i < clients.size(); i++) {
                    Socket client = clients.get(i).socket;
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
        ServerUser serverUser ;
        public ListenerClient( ServerUser serverUser) {
            this.serverUser = serverUser;
            this.client = serverUser.socket;
        }
        //为每一个客户端创建线程等待接收信息，然后把信息广播出去
        public void run() {
            ChatMessage clientMessage = null;
            while (true) {
                try {
                    objectInputStream = new ObjectInputStream(client.getInputStream());
                    Object object = objectInputStream.readObject();
                    if(object instanceof  ChatMessage){
                        clientMessage = (ChatMessage) object;
                        println("收到消息包");
                        println(clientMessage == null ? "null" : clientMessage.toString());
                        if(clientMessage!=null && clientMessage.getChatType() == ChatMessage.SINGLE_CHAT){
                            sendSingleChatMsg(clientMessage);
                        } else if(clientMessage!=null && clientMessage.getChatType() == ChatMessage.FRIEND_LIST){
                            sendFriendList();
                        }

                    } else if(object instanceof EmptyMessage){
                        EmptyMessage emptyMessage = (EmptyMessage) object;
                        println(serverUser.count + " 收到心跳包");
                        serverUser.count = 0;
                    }


                } catch (IOException e) {
                    println("read:" + e.toString());
                    break;
                } catch (ClassNotFoundException e) {
                    println("read:" + e.toString());
                } catch (Exception e){
                    println("read:" + e.toString());
                }
            }
        }
        //把信息广播到所有用户
        public synchronized void sendMutilChatMsg(ChatMessage  clientMessage) {
            try {
                for (int i = 0; i < clients.size(); i++) {
                    Socket client = clients.get(i).socket;
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

        //
        public synchronized void sendSingleChatMsg(ChatMessage  clientMessage) {
            try {
                for (int i = 0; i < clients.size(); i++) {
                    Socket client = clients.get(i).socket;
                    System.out.println("ips :" + client.getInetAddress().toString());
                    if(client.getInetAddress().equals(clientMessage.getToAddress())){
                        if(!client.isClosed()){
                            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                            objectOutputStream.writeObject(clientMessage);
                            objectOutputStream.flush();
                        }else{
                            println(">>closed!");
                        }
                    }
                }

            } catch (Exception e) {
                println("write:" + e.toString());
            }
        }

        public synchronized void sendFriendList() {
            if(!client.isClosed()){
                try {
                    objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setChatType(ChatMessage.FRIEND_LIST);
                    chatMessage.setBody(clients.toString());
                    objectOutputStream.writeObject(chatMessage);
                    objectOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                println(">>closed!");
            }
        }

        //心跳策略:http://www.cnblogs.com/scy251147/p/3333957.html
        private void startCountAddTimerTask() {
            // 启动心跳线程
            Timer countAddTimer = new Timer();
            TimerTask countAddTask = new TimerTask() {

                @Override
                public void run() {
                    serverUser.count ++;
                    if(serverUser.count > 3){
                        println("检测到用户：" + client.getInetAddress() + "掉线");
                    }
                }
            };
            countAddTimer.schedule(countAddTask, 5000, 5000);
        }



        public void println(String s) {
            if (s != null) {
                System.out.println(s + "\n");
            }
        }
    }
}
