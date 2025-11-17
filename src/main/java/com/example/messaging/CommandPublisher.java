package com.example.messaging;

import com.example.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandPublisher {

  private final Logger logger = LogManager.getLogger(CommandPublisher.class);

  private final CommandBroker broker;

  public CommandPublisher(CommandBroker broker) {
    this.broker = broker;
  }

  public void publish(Command command) {
    logger.info("Publishing command: {}", command.getClass().getSimpleName());
    broker.publish(command);
  }

}
