package com.etoak.p2p.webscoket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wang
 */
public class ChatRoomServer extends ServerSocket {

    /**
     * 设置服务端的端口号与IP地址
     */
    // private static final String SERVER_IP = "172.20.10.7";

    private static final int SERVER_PORT = 8899;

    private static final String VIEW_USER = "viewUser";


    private static List<String> userList = new CopyOnWriteArrayList<>();
    /**
     * 服务器已启用线程的集合
     */
    private static List<Task> threadList = new ArrayList<>();

    /**
     * 存放消息的队列
     */
    private static BlockingQueue<String> msgQueue = new ArrayBlockingQueue<String>(20);

    public ChatRoomServer() throws Exception {
        super(SERVER_PORT);
    }

    public void load() throws Exception {
        new Thread(new PushMsgTask()).start();
        while (true) {

        }
    }

    /**
     * 从消息队列中取消息
     */
    class PushMsgTask implements Runnable {
        @Override
        public void run() {

        }
    }

    /**
     * 处理客户端发来的消息 线程类
     */
    class Task implements Runnable {

        private Socket socket;

        private BufferedReader buff;

        private Writer writer;


        /**
         * 成员名称
         */
        private String userName;

        public Task(Socket socket) {

            this.socket = socket;
            this.userName = String.valueOf(socket.getPort());

            try {
                this.buff = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                this.writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            userList.add(userName);
            threadList.add(this);
            pushMsg("【" + this.userName + " 进入了聊天室。】");
            System.out.println("From Client[port: " + socket.getPort() + "] " + this.userName + " 进入了聊天室");

        }

        /**
         * 吧要发送的存入消息队列
         * @param msg
         */
        private void pushMsg(String msg) {

            try {
                msgQueue.put(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * 发送消息
         * @param msg
         */
        private void sendMsg(String msg){

            try {
                writer.write(msg);
                writer.write("\015\012");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String onlineUsers(){
            StringBuffer sbf = new StringBuffer();
            sbf.append("===========在线成员列表(").append(userList.size()).append(")============\015\012");
            for(int i=0;i<userList.size();i++){
                sbf.append("[ "+userList.get(i)+" ]\015\012");
            }
            sbf.append("==========================");
            return sbf.toString();
        }

        @Override
        public void run() {
            try {
                while (true){

                        String msg = buff.readLine();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
