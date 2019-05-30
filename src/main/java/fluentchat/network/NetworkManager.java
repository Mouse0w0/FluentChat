package fluentchat.network;

import java.util.HashMap;
import java.util.Map;

public class NetworkManager {

    private final Map<Class<? extends Message>, RegisteredMessage<? extends Message>> registeredMessages = new HashMap<>();
    private final Map<Integer, RegisteredMessage<? extends Message>> idToMessage = new HashMap<>();

    private int nextId;

    public <T extends Message> void register(Class<T> clazz, MessageSerializer<T> serializer, MessageHandler<T> handler) {
        int id = nextId++;
        RegisteredMessage<T> registeredMessage = new RegisteredMessage<>(id, clazz, serializer, handler);
        registeredMessages.put(clazz, registeredMessage);
        idToMessage.put(id, registeredMessage);
    }

    public <T extends Message> RegisteredMessage<T> getRegisteredMessage(Class<T> clazz) {
        return (RegisteredMessage<T>) registeredMessages.get(clazz);
    }

    public RegisteredMessage<? extends Message> getRegisteredMessage(int id) {
        return idToMessage.get(id);
    }
}
