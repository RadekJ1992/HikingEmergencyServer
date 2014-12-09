package pl.rj.hikingemergency.utils;

import pl.rj.hikingemergency.model.Message;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by radoslawjarzynka on 09.12.14.
 */
public class MessagesHandler {

    private volatile ConcurrentLinkedQueue<Message> incomingMessages;

    private static volatile MessagesHandler instance = new MessagesHandler();

    public static MessagesHandler getInstance() {
        return instance;
    }

    private MessagesHandler() {
        incomingMessages = new ConcurrentLinkedQueue<>();
    }

    public void addMessage(Message message) {
        incomingMessages.add(message);
    }

    public Message getMessage() {
        return incomingMessages.poll();
    }
}