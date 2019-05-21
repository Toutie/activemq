package com.etoak.p2p.topic;

import com.etoak.HelloListener;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author wang
 */
public class TopicScubscriber {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory(null,null,"tcp://localhost:61616");

        Connection connection = factory.createConnection();

        connection.start();

        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        Topic topic = session.createTopic("hello-Topic");

        MessageConsumer consumer = session.createConsumer(topic);

       consumer.setMessageListener(new MessageListener() {
           @Override
           public void onMessage(Message message) {
              TextMessage textMessage = (TextMessage)message;

               try {
                   textMessage.acknowledge();
                   System.out.println("----11"+textMessage.getText().toString());
               } catch (JMSException e) {
                   e.printStackTrace();
               }

           }
       });


    }
}
