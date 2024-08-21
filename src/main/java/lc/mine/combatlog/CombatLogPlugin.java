package lc.mine.combatlog;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import lc.mine.combatlog.command.CombatLogReloadCommand;
import lc.mine.combatlog.hook.PlaceholderApiHook;
import lc.mine.combatlog.listener.data.ListenerRegister;
import lc.mine.combatlog.listener.PlayerDamageListener;
import lc.mine.combatlog.listener.PlayerDeathListener;
import lc.mine.combatlog.listener.PlayerQuitListener;
import lc.mine.combatlog.listener.PreCommandListener;
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
        final WeakHashMap<UUID, PlayerInCombat> playersInCombat = new WeakHashMap<>();
        final CombatUtils untagActions = new CombatUtils(core.getData(), OPTIONS);

        new ListenerRegister(this).register(
            new PlayerDeathListener(untagActions, playersInCombat),
            new PlayerQuitListener(untagActions, playersInCombat),
            new PlayerDamageListener(playersInCombat),
            new PreCommandListener(OPTIONS, playersInCombat)
        );

        getCommand("combatreload").setExecutor(new CombatLogReloadCommand(this));
    }

    public void load() {
        saveDefaultConfig();
        new StartMessages().start(this);

        final FileConfiguration config = getConfig();
        OPTIONS.setLcoinsOnKill(config.getInt("kill-lcoins"));            
        OPTIONS.setPvpTagTime((int)(config.getDouble("tag-duration") * 1000D));
        OPTIONS.setKillsToLevelup(config.getInt("levelup-need-kills"));
        final List<String> commands = config.getStringList("combat-block-commands");
        OPTIONS.setBlockCommands((commands == null || commands.isEmpty()) ? Set.of() : Set.copyOf(commands));
    }
}
