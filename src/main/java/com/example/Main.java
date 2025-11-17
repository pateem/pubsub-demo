package com.example;


import com.example.command.UserCommandFactory;
import com.example.messaging.CommandBroker;
import com.example.messaging.CommandPublisher;
import com.example.messaging.CommandSubscriber;
import com.example.messaging.InMemoryCommandBroker;
import com.example.persistence.H2ConnectionProvider;
import com.example.persistence.UserDaoImpl;

public class Main {

  public static void main(String[] args) {

    UserDaoImpl userDao = new UserDaoImpl(new H2ConnectionProvider());
    UserCommandFactory userCommandFactory = new UserCommandFactory(userDao);

    CommandBroker commandBroker = new InMemoryCommandBroker();
    CommandSubscriber subscriber = new CommandSubscriber();
    commandBroker.subscribe(subscriber);
    CommandPublisher publisher = new CommandPublisher(commandBroker);

    publisher.publish(userCommandFactory.add(1L, "a1", "Robert"));
    publisher.publish(userCommandFactory.add(2L, "a2", "Martin"));
    publisher.publish(userCommandFactory.printAll());
    publisher.publish(userCommandFactory.deleteAll());
    publisher.publish(userCommandFactory.printAll());
  }
}