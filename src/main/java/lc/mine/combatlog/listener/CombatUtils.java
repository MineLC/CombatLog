package lc.mine.combatlog.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import lc.mine.combatlog.message.Message;
import lc.mine.combatlog.storage.Options;
import lc.mine.core.database.Database;
import lc.mine.core.database.PlayerData;

public final class CombatUtils {

    private final Database database;
    private final Options options;

    public CombatUtils(Database database, Options options) {
        this.database = database;
        this.options = options;
    }

    public void execute(final DamageCause cause, final Player victim, final Player killer) {
        sendMessage(cause, victim, killer);

        PlayerData killerData = database.getCached(killer.getUniqueId());
        if (killerData == null) {
            killerData = new PlayerData.New(killer.getName());
            database.create(killer, killerData);
        }
        killerData.setLcoins(killerData.getLcoins() + options.getLcoinsOnKill());
        Message.get().send(killer, "kill-reward");
    }

    public DamageCause getCause(final Player player, final DamageCause fallback) {
        if (player.getLastDamageCause() == null) {
            return fallback;
        }
        return player.getLastDamageCause().getCause();
    }

    public void sendMessage(final DamageCause cause, final Player victim, final Player killer) {
        String message = Message.get().message(cause.name())
            .replace("%v%", victim.getName());
        if (killer != null) {
            message += Message.get().message("if-killer-exist").replace("%k%", killer.getName());
        }
        Bukkit.broadcastMessage(message);
    }

    public Options getOptions() {
        return options;
    }
}