package com.etoak.p2p.first;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author wang
 */
public class HelloProvider {

    public static void main(String[] args) throws JMSException {

        ConnectionFactory factory = new ActiveMQConnectionFactory(null,null,"tcp://localhost:61616");


        Connection connection = factory.createConnection();

        connection.start();


        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue("Hello-queue");


        MessageProducer producer = session.createProducer(queue);

        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        TextMessage textMessage = session.createTextMessage();
        for (int i=0;i< 10 ;i++){
            textMessage.setText(String.format("这是第%s条消息",i+1));
            producer.send(textMessage);

        }
        producer.close();
        session.close();
        connection.close();

    }
}
