package com.example.messaging;

import com.example.command.Command;

public interface CommandBroker {

  void subscribe(CommandSubscriber subscriber);

  void publish(Command command);

}
