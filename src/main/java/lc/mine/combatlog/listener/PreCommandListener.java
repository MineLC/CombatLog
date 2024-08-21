package lc.mine.combatlog.listener;

import java.util.UUID;
import java.util.WeakHashMap;

import org.apache.commons.lang.StringUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import lc.mine.combatlog.message.Message;
import lc.mine.combatlog.storage.Options;
import lc.mine.combatlog.storage.PlayerInCombat;

public final class PreCommandListener implements Listener {
    private final Options options;
    private final WeakHashMap<UUID, PlayerInCombat> playersInCombat;

    public PreCommandListener(Options options, WeakHashMap<UUID, PlayerInCombat> playersInCombat) {
        this.options = options;
        this.playersInCombat = playersInCombat;
    }

    @EventHandler
    public void handle(final PlayerCommandPreprocessEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!options.getBlockCommands().contains(StringUtils.split(event.getMessage(), ' ')[0])) {
            return;
        }
        final PlayerInCombat combat = playersInCombat.get(event.getPlayer().getUniqueId());
        if (combat == null) {
            return;
        }
        final long diference = (System.currentTimeMillis() - combat.getTime());
        if (diference < options.getPvpTagTime()) {
            event.getPlayer().sendMessage(Message.get().message("spawn-time-wait").replace("%time%", String.valueOf(options.getPvpTagTime() - (diference/1000))));
            event.setCancelled(true);
            return;
        }
        playersInCombat.remove(event.getPlayer().getUniqueId());
    }
}