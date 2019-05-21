package com.etoak.p2p.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author wang
 */
public class TopicPublisher {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory(null,null,"tcp://localhost:61616");

        Connection connection = factory.createConnection();

        connection.start();

        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        Topic topic = session.createTopic("hello-Topic");

        MessageProducer producer = session.createProducer(topic);

        TextMessage message = session.createTextMessage("这是一条topic----");

        producer.send(message);
        System.out.println("over");
        producer.close();
        session.close();
        connection.close();

    }
}
