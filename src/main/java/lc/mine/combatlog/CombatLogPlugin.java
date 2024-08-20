package lc.mine.combatlog;

import java.util.UUID;
import java.util.WeakHashMap;

import org.bukkit.plugin.java.JavaPlugin;

import lc.mine.combatlog.command.CombatLogReloadCommand;
import lc.mine.combatlog.hook.PlaceholderApiHook;
import lc.mine.combatlog.listener.data.ListenerRegister;
import lc.mine.combatlog.listener.PlayerDamageListener;
import lc.mine.combatlog.listener.PlayerDeathListener;
import lc.mine.combatlog.listener.PlayerQuitListener;
import lc.mine.combatlog.listener.CombatUtils;
import lc.mine.combatlog.message.StartMessages;
import lc.mine.combatlog.storage.Options;
import lc.mine.combatlog.storage.PlayerInCombat;
import lc.mine.core.CorePlugin;

public class CombatLogPlugin extends JavaPlugin {

    private static final Options OPTIONS = new Options();

    @Override
    public void onEnable() {
        if (!(getServer().getPluginManager().getPlugin("LCCore") instanceof CorePlugin core)) {
            getLogger().warning("Combatlog can't start without lccore");
            return;
        }
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderApiHook(OPTIONS).register();
        }

        load();
        getCommand("combatreload").setExecutor(new CombatLogReloadCommand(this));

        final WeakHashMap<UUID, PlayerInCombat> playersInCombat = new WeakHashMap<>();
        final CombatUtils untagActions = new CombatUtils(core.getData(), OPTIONS);

        new ListenerRegister(this).register(
            new PlayerDeathListener(untagActions, playersInCombat),
            new PlayerQuitListener(untagActions, playersInCombat),
            new PlayerDamageListener(playersInCombat)
        );
    }

    public void load() {
        saveDefaultConfig();
        new StartMessages().start(this);

        OPTIONS.setLcoinsOnKill(getConfig().getInt("kill-lcoins"));            
        OPTIONS.setPvpTagTime((int)(getConfig().getDouble("tag-duration") * 1000D));
    }
}
