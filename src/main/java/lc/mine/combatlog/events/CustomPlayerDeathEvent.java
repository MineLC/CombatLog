package lc.mine.combatlog.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Nullable;

public class CustomPlayerDeathEvent extends Event {

    private final HandlerList HANDLERS = new HandlerList();

    private final boolean isInCombat;
    private final Player victim;
    private final @Nullable Player killer;

    public CustomPlayerDeathEvent(Player victim, Player killer, boolean isInCombat) {
        this.victim = victim;
        this.killer = killer;
        this.isInCombat = isInCombat;
    }

    public @Nullable Player getKiller() {
        return killer;
    }
    public Player getVictim() {
        return victim;
    }
    public boolean isInCombat() {
        return isInCombat;
    }
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
