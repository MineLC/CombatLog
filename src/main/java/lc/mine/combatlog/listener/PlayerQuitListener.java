package lc.mine.combatlog.listener;

import java.util.UUID;
import java.util.WeakHashMap;

import org.bukkit.Statistic;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerQuitEvent;

import lc.mine.combatlog.listener.data.EventListener;
import lc.mine.combatlog.message.Message;
import lc.mine.combatlog.storage.PlayerInCombat;

public final class PlayerQuitListener implements EventListener<PlayerQuitEvent> {
    private final WeakHashMap<UUID, PlayerInCombat> playersInCombat;
    private final CombatUtils untag;

    public PlayerQuitListener(CombatUtils untag, WeakHashMap<UUID, PlayerInCombat> playersInCombat) {
        this.playersInCombat = playersInCombat;
        this.untag = untag;
    }

    @Override
    public void handle(final PlayerQuitEvent event) {
        final PlayerInCombat combat = playersInCombat.remove(event.getPlayer().getUniqueId());
        if (combat == null || (System.currentTimeMillis() - combat.getTime() > untag.getOptions().getPvpTagTime())) {
            return;
        }
        Message.get().send(combat.getPlayer(), "combat");
        untag.execute(untag.getCause(event.getPlayer(), DamageCause.ENTITY_ATTACK), event.getPlayer(), combat.getPlayer());
        combat.getPlayer().incrementStatistic(Statistic.PLAYER_KILLS, 1);
        event.getPlayer().incrementStatistic(Statistic.DEATHS, 1);
    }
}