package com.etoak.p2p.selector;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author wang
 */
public class SelectorConsumer {
    private static final String AGE_SELECTOR ="age=10";

    public static void main(String[] args) throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory(null,null,"tcp://localhost:61616");

        Connection connection = factory.createConnection();
        connection.start();

         final Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);

        Queue queue = session.createQueue("selector");

        MessageConsumer consumer = session.createConsumer(queue);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message!=null && message instanceof MapMessage){
                    MapMessage mapMessage = (MapMessage)message;


                    try {
                        mapMessage.acknowledge();
                        session.commit();

                        String name = mapMessage.getString("name");
                        String address = mapMessage.getString("address");

                        System.out.println("name: "+ name +" adress: "+address);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }

                }
            }
        });


    }
}
