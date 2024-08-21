package lc.mine.combatlog.listener;

import java.util.UUID;
import java.util.WeakHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lc.mine.combatlog.message.Message;
import lc.mine.combatlog.storage.PlayerInCombat;

public final class PlayerDamageListener implements Listener {

    private final WeakHashMap<UUID, PlayerInCombat> playersInCombat;

    public PlayerDamageListener(WeakHashMap<UUID, PlayerInCombat> playersInCombat) {
        this.playersInCombat = playersInCombat;
    }

    @EventHandler
    public void handle(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim)
            || !(event.getDamager() instanceof Player damager)
            || (damager.equals(victim)))
        {
            return;
        }
        final long time = System.currentTimeMillis();
        setInCombat(victim, damager, time);
        setInCombat(damager, victim, time);
    }

    private void setInCombat(final Player player, final Player other, final long time) {
        final PlayerInCombat combat = playersInCombat.get(player.getUniqueId());
        if (combat == null) {
            playersInCombat.put(player.getUniqueId(), new PlayerInCombat(other, time));
            Message.get().send(player, "combat");
            return;
        }
        combat.setPlayer(other);
        combat.setTime(time);
    }
}