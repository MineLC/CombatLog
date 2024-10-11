package lc.mine.combatlog.listener;

import java.util.UUID;
import java.util.WeakHashMap;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerQuitEvent;

import lc.mine.combatlog.message.Message;
import lc.mine.combatlog.storage.PlayerInCombat;

public final class PlayerQuitListener implements Listener {
    private final WeakHashMap<UUID, PlayerInCombat> playersInCombat;
    private final CombatUtils untag;

    public PlayerQuitListener(CombatUtils untag, WeakHashMap<UUID, PlayerInCombat> playersInCombat) {
        this.playersInCombat = playersInCombat;
        this.untag = untag;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handle(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final PlayerInCombat combat = playersInCombat.remove(event.getPlayer().getUniqueId());
        if (combat == null) {
            return;
        }
        final PlayerInCombat enemy = playersInCombat.get(combat.getPlayer().getUniqueId());
        if (enemy != null && enemy.getPlayer().equals(player)) {
            playersInCombat.remove(combat.getPlayer().getUniqueId());
        }

        Message.get().send(combat.getPlayer(), "combat");
        untag.execute(untag.getCause(event.getPlayer(), DamageCause.ENTITY_ATTACK), player, combat.getPlayer());

        combat.getPlayer().incrementStatistic(Statistic.PLAYER_KILLS, 1);
        event.getPlayer().incrementStatistic(Statistic.DEATHS, 1);   
    }
}