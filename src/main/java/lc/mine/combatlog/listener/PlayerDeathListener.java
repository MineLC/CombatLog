package lc.mine.combatlog.listener;

import java.util.UUID;
import java.util.WeakHashMap;

import org.bukkit.Sound;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import lc.mine.combatlog.listener.data.EventListener;
import lc.mine.combatlog.storage.PlayerInCombat;

public class PlayerDeathListener implements EventListener<PlayerDeathEvent> {

    private final WeakHashMap<UUID, PlayerInCombat> playersInCombat;
    private final CombatUtils untag;

    public PlayerDeathListener(CombatUtils untag, WeakHashMap<UUID, PlayerInCombat> playersInCombat) {
        this.playersInCombat = playersInCombat;
        this.untag = untag;
    }

    @Override
    public void handle(final PlayerDeathEvent event) {
        PlayerInCombat combat = playersInCombat.remove(event.getEntity().getUniqueId());
        event.setDeathMessage(null);
        if (combat == null || (System.currentTimeMillis() - combat.getTime() > untag.getOptions().getPvpTagTime())) {
            untag.sendMessage(untag.getCause(event.getEntity(), DamageCause.SUICIDE), event.getEntity(), null);
            return;
        }
        event.getEntity().playSound(event.getEntity().getLocation(), Sound.BAT_DEATH, 1.0f, 1.0f);
        if (event.getEntity().getKiller() != null) {
            untag.execute(untag.getCause(event.getEntity(), DamageCause.ENTITY_ATTACK), event.getEntity(), event.getEntity().getKiller());
        }
    }

    @Override
    public Class<PlayerDeathEvent> eventClass() {
        return PlayerDeathEvent.class;
    }
}