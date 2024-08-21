package lc.mine.combatlog.listener;

import java.util.UUID;
import java.util.WeakHashMap;

import org.bukkit.Sound;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import lc.mine.combatlog.CombatLogPlugin;
import lc.mine.combatlog.nms.v1_8R3.v1_8R3;
import lc.mine.combatlog.storage.PlayerInCombat;
import lc.mine.combatlog.storage.title.Title;

public class PlayerDeathListener implements Listener {

    private final WeakHashMap<UUID, PlayerInCombat> playersInCombat;
    private final CombatUtils untag;
    private final CombatLogPlugin plugin;

    public PlayerDeathListener(CombatLogPlugin plugin, CombatUtils untag, WeakHashMap<UUID, PlayerInCombat> playersInCombat) {
        this.plugin = plugin;
        this.playersInCombat = playersInCombat;
        this.untag = untag;
    }

    @EventHandler
    public void handle(final PlayerDeathEvent event) {
        PlayerInCombat combat = playersInCombat.remove(event.getEntity().getUniqueId());
        event.setDeathMessage(null);
        if (untag.getOptions().getInstantRespawn()) {
            plugin.getServer().getScheduler().runTask(plugin, () -> event.getEntity().spigot().respawn());
        }
        if (untag.getOptions().getTitleData() != null) {
            final Title title = untag.getOptions().getTitleData().next();
            v1_8R3.sendTitle(event.getEntity(), title.title(), false);
            if (title.subtitle() != null) {
                v1_8R3.sendTitle(event.getEntity(), title.subtitle(), true);    
            }
        }

        if (combat == null || (System.currentTimeMillis() - combat.getTime() > untag.getOptions().getPvpTagTime())) {
            untag.sendMessage(untag.getCause(event.getEntity(), DamageCause.SUICIDE), event.getEntity(), null);
            return;
        }
        event.getEntity().playSound(event.getEntity().getLocation(), Sound.BAT_DEATH, 1.0f, 1.0f);
        if (event.getEntity().getKiller() != null) {
            untag.execute(untag.getCause(event.getEntity(), DamageCause.ENTITY_ATTACK), event.getEntity(), event.getEntity().getKiller());
        }
    }
}