package programmerzamannow.rabbitmq;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;

public class ConsumerApp {

  public static void main(String[] args) throws Exception{
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://guest:guest@localhost:5672/");
    factory.setVirtualHost("/");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    DeliverCallback deliverCallback = (consumerTag, message) -> {
      System.out.println(message.getEnvelope().getRoutingKey());
      System.out.println(new String(message.getBody()));
    };

    CancelCallback cancelCallback = consumerTag -> {
      System.out.println("Consumer is canceled");
    };

    channel.basicConsume("whatsapp", true, deliverCallback, cancelCallback);

//    channel.close();
//    connection.close();
  }
}
