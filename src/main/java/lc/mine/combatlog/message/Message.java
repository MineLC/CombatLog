package lc.mine.combatlog.message;

import java.util.Map;

import org.bukkit.command.CommandSender;

public final class Message {
    
    private static Message message;

    private final Map<String, String> messages;
    
    Message(Map<String, String> messages) {
        this.messages = messages;
    }

    public String message(String key) {
        final String message = messages.get(key);
        return (message == null) ? "" : message;
    }
    public void send(final CommandSender sender, final String key) {
        final String message = message(key);
        if (message != null) {
            sender.sendMessage(message);
        }
    }

    public static Message get() {
        return message;
    }

    static void set(Message message) {
        Message.message = message;
    }
}
