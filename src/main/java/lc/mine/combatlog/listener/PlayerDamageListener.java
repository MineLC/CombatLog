package lc.mine.combatlog.listener;

import java.util.UUID;
import java.util.WeakHashMap;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lc.mine.combatlog.message.Message;
import lc.mine.combatlog.storage.PlayerInCombat;

public final class PlayerDamageListener implements Listener {

    private final WeakHashMap<UUID, PlayerInCombat> playersInCombat;

    public PlayerDamageListener(WeakHashMap<UUID, PlayerInCombat> playersInCombat) {
        this.playersInCombat = playersInCombat;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void handleDamage(final EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim)) {
            return;
        }

        Player damager;
        if (event.getDamager() instanceof Projectile projectile) {
            if (!(projectile.getShooter() instanceof Player shooter)) {
                return;
            }
            damager = shooter;
        } else {
            if (!(event.getDamager() instanceof Player)) {
                return;
            }
            damager = (Player)event.getDamager();
        }

        final long time = System.currentTimeMillis();

        setInCombat(victim, damager, time);
        setInCombat(damager, victim, time);
    }

    private void setInCombat(final Player player, final Player other, final long time) {
        PlayerInCombat combat = playersInCombat.get(player.getUniqueId());
        if (combat == null) {
            playersInCombat.put(player.getUniqueId(), new PlayerInCombat(other, time));
            Message.get().send(player, "combat");
            return;
        }
        combat.setPlayer(other);
        combat.setTime(time);
    }
}