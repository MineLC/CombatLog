package lc.mine.combatlog.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Nullable;

public class PlayerCombatLogEvent extends Event {

    private final HandlerList HANDLERS = new HandlerList();

    private String deathMessage;
    private boolean cancelDeathMessage = false;
    private final Player victim;
    private final @Nullable Player killer;
    private final boolean hasCombatTag;

    public PlayerCombatLogEvent(Player victim, Player killer, String deathMessage) {
        this.victim = victim;
        this.killer = killer;
        this.deathMessage = deathMessage;
        this.hasCombatTag = killer != null;
    }

    public @Nullable Player getKiller() {
        return killer;
    }
    public Player getVictim() {
        return victim;
    }
    public String getDeathMessage() {
        return deathMessage;
    }

    public void setCancelDeathMessage(boolean cancelDeathMessage) {
        this.cancelDeathMessage = cancelDeathMessage;
    }
    public boolean isCancelDeathMessage() {
        return cancelDeathMessage;
    }
    public boolean isHasCombatTag() {
        return hasCombatTag;
    }
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
