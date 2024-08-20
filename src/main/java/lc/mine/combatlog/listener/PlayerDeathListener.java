package lc.mine.combatlog.listener;

import java.util.UUID;
import java.util.WeakHashMap;

import org.bukkit.event.entity.PlayerDeathEvent;

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
        if (combat == null || (System.currentTimeMillis() - combat.getTime() > untag.getOptions().getPvpTagTime())) {
            untag.sendMessage(event.getEntity().getLastDamageCause().getCause(), event.getEntity(), null);
            return;
        }
        if (event.getEntity().getKiller() != null) {
            untag.execute(event.getEntity().getLastDamageCause().getCause(), event.getEntity(), event.getEntity().getKiller());
            event.setDeathMessage(null);
        }
    }

    @Override
    public Class<PlayerDeathEvent> eventClass() {
        return PlayerDeathEvent.class;
    }
}