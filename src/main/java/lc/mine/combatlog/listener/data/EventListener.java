package lc.mine.combatlog.listener.data;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public interface EventListener<T extends Event> extends Listener {
    void handle(final T event);

    default Class<T> eventClass() {
        return null;
    }
}
