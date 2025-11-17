package com.example.messaging;

import com.example.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandSubscriber {

    private final Logger logger = LogManager.getLogger(CommandSubscriber.class);

    void onCommand(Command command) {
        logger.info("Executing command: {}", command.getClass().getSimpleName());
        command.execute();
    }

}
