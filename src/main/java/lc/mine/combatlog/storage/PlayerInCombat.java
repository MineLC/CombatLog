package lc.mine.combatlog.storage;

import org.bukkit.entity.Player;

public final class PlayerInCombat {
    private Player player;
    private long time;

    public PlayerInCombat(Player player, long time) {
        this.player = player;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
