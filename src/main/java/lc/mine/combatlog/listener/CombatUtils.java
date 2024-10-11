package lc.mine.combatlog.listener;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import lc.mine.combatlog.events.PlayerDeathCombatLog;
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

        final PlayerData killerData = database.getCached(killer.getUniqueId());
        killerData.setLcoins(killerData.getLcoins() + options.getLcoinsOnKill());
        Message.get().send(killer, "kill-reward");
    
        Bukkit.getServer().getPluginManager().callEvent(new PlayerDeathCombatLog(victim, killer));
    }

    public DamageCause getCause(final Player player, final DamageCause fallback) {
        final EntityDamageEvent lastDamageCause = player.getLastDamageCause();
        if (lastDamageCause == null) {
            return fallback;
        }
        return lastDamageCause.getCause();
    }

    public void sendMessage(final DamageCause cause, final Player victim, final Player killer) {
        String message = Message.get().message(cause.name())
            .replace("%v%", victim.getName());
        if (killer != null) {
            message += Message.get().message("if-killer-exist").replace("%k%", killer.getName());
            killer.playSound(killer.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
            killer.getWorld().playEffect(victim.getLocation(), Effect.EXPLOSION_LARGE, 1);
        }
        Bukkit.broadcastMessage(message);
    }

    public Options getOptions() {
        return options;
    }
}