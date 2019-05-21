package com.etoak.p2p.first;

import com.etoak.HelloListener;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author wang
 */
public class HelloConsumer {

    public static void main(String[] args) throws JMSException, InterruptedException {
        //1.创建ConnectionFactory
        ConnectionFactory factory = new ActiveMQConnectionFactory(null, null, "tcp://localhost:61616");


        //2.创建Connection
        Connection connection = factory.createConnection();
        connection.start();
        //3.创建session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建Destination
        Queue queue = session.createQueue("Hello-queue");
        //5.创建消费者
        MessageConsumer consumer = session.createConsumer(queue);
        //6.消费消息

        /**
         * 第一种消费方式
         */
//        for (int i = 0; i < 100; i++) {
//            Thread.sleep(500);
//            Message message = consumer.receive();
//            if (message != null) {
//                TextMessage textMessage = (TextMessage) message;
//                System.out.println(textMessage.getText().toString());
//            } else {
//                break;
//            }
//        }

        consumer.setMessageListener(new HelloListener());

//        consumer.close();
//        session.close();
//        connection.close();

    }

}
