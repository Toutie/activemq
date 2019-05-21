package com.etoak.p2p.webscoket;

import java.io.*;
import java.net.Socket;

/**
 * 聊天客户端
 *
 * @author wang
 */
public class ChatRoomClient extends Socket {

    /**
     * 设置监听服务端的端口号与IP地址
     */
    private static final String SERVER_IP = "172.20.10.7";

    private static final int SERVER_PORT = 8899;

    private static final String END_MARK = "quit";

    /**
     * socket
     */
    private Socket client;

    private Writer writer;

    /**
     * 发送消息输入流
     */
    private BufferedReader in;


    /**
     * 构造方法 与服务端建立连接
     */
    public ChatRoomClient() throws Exception {
        super(SERVER_IP, SERVER_PORT);
        this.client = this;
        System.out.println("Client[port: " + client.getLocalPort() + "] 您已进入聊天室");
    }

    /**
     * 启动监听收取消息，循环可以不停的输入消息，将消息发送出去
     *
     * @throws IOException
     */
    public void load() throws IOException {
        this.writer = new OutputStreamWriter(this.getOutputStream(), "UTF-8");
        //启动监听
        new Thread(new ReceiveMsgTask()).start();
        while (true) {
            in = new BufferedReader(new InputStreamReader(System.in));
            String inputMsg = in.readLine();
            if (inputMsg != null) {
                writer.write(inputMsg);
                writer.write("\n");
            }
            writer.flush();
        }
    }


    /**
     * 监听服务器发来的消息 线程类
     */
    class ReceiveMsgTask implements Runnable {


        private BufferedReader buff;


        @Override
        public void run() {
            try {
                this.buff = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
                while (true) {
                    String result = buff.readLine();
                    if (END_MARK.equals(result)) {
                        System.out.println("Client[port:" + client.getLocalPort() + "] 您已退出聊天室");
                        break;
                    } else {
                        System.out.println(result);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                try {
                    client.close();
                    buff.close();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ChatRoomClient client = new ChatRoomClient();

        client.load();

    }


}
