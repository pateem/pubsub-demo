package com.example.messaging;

import com.example.command.Command;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class InMemoryCommandBroker implements CommandBroker {

    private final List<CommandSubscriber> subscribers = new CopyOnWriteArrayList<>();
    private final BlockingQueue<Command> commandsQueue = new LinkedBlockingQueue<>();
    private final ExecutorService deliveryThread = Executors.newSingleThreadExecutor();
    private final AtomicBoolean deliveryStarted = new AtomicBoolean(false);


    public void subscribe(CommandSubscriber subscriber) {
        subscribers.add(subscriber);
        if (deliveryStarted.compareAndSet(false, true)) {
            startDelivery();
        }
    }

    public void publish(Command command) {
        commandsQueue.add(command);
    }

    private void startDelivery() {
        deliveryThread.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Command command = commandsQueue.take();
                    for (CommandSubscriber subscriber : subscribers) {
                        subscriber.onCommand(command);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Command delivery thread interrupted", e);
                }
            }
        });
    }

}
