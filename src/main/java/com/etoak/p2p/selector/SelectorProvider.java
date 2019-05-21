package com.etoak.p2p.selector;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author wang
 */
public class SelectorProvider {


    private static final String USERNAME = ActiveMQConnectionFactory.DEFAULT_USER;

    private static final String PASSWORD = ActiveMQConnectionFactory.DEFAULT_PASSWORD;

    private static final String BROKER_URL = ActiveMQConnectionFactory.DEFAULT_BROKER_URL;

    private static ConnectionFactory factory;

    private static Connection connection;

    private static Session session;

    private static Queue queue;

    private static MessageProducer producer;

    static {

        factory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKER_URL);


        try {
             connection = factory.createConnection();
            connection.start();

            session =  connection.createSession(true,Session.CLIENT_ACKNOWLEDGE);

            queue= session.createQueue("selector");

            producer = session.createProducer(queue);

            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }


    public static void sendMsg() throws JMSException {
        MapMessage mapMessage = session.createMapMessage();

        mapMessage.setString("name","etoak");
        mapMessage.setString("address","趵突泉北路");
        mapMessage.setIntProperty("age",10);

        MapMessage mapMessage2 = session.createMapMessage();
        mapMessage2.setString("name1","etoak1");
        mapMessage2.setString("address1","山大路数码港");
        mapMessage2.setInt("age1",2);

        producer.send(mapMessage);
        producer.send(mapMessage2);

        //一定要提交！
        session.commit();
    }

    public static void close() throws JMSException {

        if (producer!=null){
            producer.close();
        }
        if (session!=null){
            session.close();
        }

        if (connection!=null) {
            connection.close();
        }

    }

    public static void main(String[] args) throws Exception{
        SelectorProvider.sendMsg();
        SelectorProvider.close();
        System.out.println("over");
    }


}
