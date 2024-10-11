package lc.mine.combatlog.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerDeathCombatLog extends Event {

    private final HandlerList HANDLERS = new HandlerList();

    private final Player victim, killer;

    public PlayerDeathCombatLog(Player victim, Player killer) {
        this.victim = victim;
        this.killer = killer;
    }

    public Player getKiller() {
        return killer;
    }
    public Player getVictim() {
        return victim;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
