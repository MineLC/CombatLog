package lc.mine.combatlog.listener;

import java.util.UUID;
import java.util.WeakHashMap;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import lc.mine.combatlog.CombatLogPlugin;
import lc.mine.combatlog.storage.PlayerInCombat;

public class PlayerDeathListener implements Listener {

    private final WeakHashMap<UUID, PlayerInCombat> playersInCombat;
    private final CombatUtils untag;
    private final CombatLogPlugin plugin;

    public PlayerDeathListener(CombatLogPlugin plugin, CombatUtils untag, WeakHashMap<UUID, PlayerInCombat> playersInCombat) {
        this.plugin = plugin;
        this.playersInCombat = playersInCombat;
        this.untag = untag;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handle(final PlayerDeathEvent event) {
        final Player victim = event.getEntity();
        final PlayerInCombat combat = playersInCombat.remove(victim.getUniqueId());

        event.setDeathMessage(null);

        if (untag.getOptions().getTitleData() != null) {
            victim.sendTitle(untag.getOptions().getTitleData().next());
        }
        if (untag.getOptions().getInstantRespawn()) {
            plugin.getServer().getScheduler().runTask(plugin, () -> victim.spigot().respawn());
        }

        victim.playSound(victim.getLocation(), Sound.BAT_DEATH, 1.0f, 1.0f);

        if (combat == null) {
            untag.sendMessage(untag.getCause(victim, DamageCause.SUICIDE), victim, null);
            return;
        }

        final PlayerInCombat enemy = playersInCombat.get(combat.getPlayer().getUniqueId());
        if (enemy.getPlayer().equals(victim)) {
            playersInCombat.remove(combat.getPlayer().getUniqueId());
        }

        if ((System.currentTimeMillis() - combat.getTime() > untag.getOptions().getPvpTagTime())) {
            untag.sendMessage(untag.getCause(victim, DamageCause.SUICIDE), victim, null);
            return;
        }
        untag.execute(untag.getCause(victim, DamageCause.ENTITY_ATTACK), victim, combat.getPlayer());
    }
}