package lc.mine.combatlog;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.github.paperspigot.Title;

import lc.mine.combatlog.command.CombatLogReloadCommand;
import lc.mine.combatlog.hook.PlaceholderApiHook;
import lc.mine.combatlog.listener.PlayerDamageListener;
import lc.mine.combatlog.listener.PlayerDeathListener;
import lc.mine.combatlog.listener.PlayerQuitListener;
import lc.mine.combatlog.listener.PreCommandListener;
import lc.mine.combatlog.listener.CombatUtils;
import lc.mine.combatlog.message.MessageColor;
import lc.mine.combatlog.message.StartMessages;
import lc.mine.combatlog.storage.Options;
import lc.mine.combatlog.storage.PlayerInCombat;
import lc.mine.combatlog.storage.title.TitleData;
import lc.mine.core.CorePlugin;

public class CombatLogPlugin extends JavaPlugin {

    private static final Options OPTIONS = new Options();

    @Override
    public void onEnable() {
        final Plugin corePlugin = getServer().getPluginManager().getPlugin("LCCore");
        if (corePlugin == null) {
            getLogger().warning("Combatlog can't start without lccore");
            return;
        }
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderApiHook(OPTIONS).register();
        }

        load();

        final WeakHashMap<UUID, PlayerInCombat> playersInCombat = new WeakHashMap<>();
        final CombatUtils untagActions = new CombatUtils(((CorePlugin)corePlugin).getData(), OPTIONS);
        final PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerDeathListener(this, untagActions, playersInCombat), this);
        pluginManager.registerEvents(new PlayerQuitListener(untagActions, playersInCombat), this);
        pluginManager.registerEvents(new PlayerDamageListener(playersInCombat), this);
        pluginManager.registerEvents(new PreCommandListener(OPTIONS, playersInCombat), this);

        getCommand("combatreload").setExecutor(new CombatLogReloadCommand(this));
    }

    public void load() {
        saveDefaultConfig();
        new StartMessages().start(this);

        final FileConfiguration config = getConfig();
        OPTIONS.setLcoinsOnKill(config.getInt("kill-lcoins"));            
        OPTIONS.setPvpTagTime((int)(config.getDouble("tag-duration") * 1000D));
        OPTIONS.setKillsToLevelup(config.getInt("levelup-need-kills"));
        OPTIONS.setInstantRespawn(config.getBoolean("instant-respawn"));

        final List<String> commands = config.getStringList("combat-block-commands");
        OPTIONS.setBlockCommands((commands == null || commands.isEmpty()) ? Set.of() : Set.copyOf(commands));
        OPTIONS.setTitleData(loadTitles(config));
    }

    private TitleData loadTitles(final FileConfiguration config) {
        final ConfigurationSection section = config.getConfigurationSection("death-titles");
        if (section == null) {
            return null;
        }
        final int fadeIn = config.getInt("title-fadeIn");
        final int stay = config.getInt("title-stay");
        final int fadeOut = config.getInt("title-fadeOut");
        final Set<String> keys = section.getKeys(false);
        final Title[] titles = new Title[keys.size()];
        int i = 0;
        for (final String key : keys) {
            final ConfigurationSection titleSection = section.getConfigurationSection(key);
            if (titleSection == null) {
                titles[i++] = new Title(key);
                continue;
            }
            titles[i++] = new Title(
                MessageColor.color(titleSection.get("title")),
                MessageColor.color(titleSection.get("subtitle")),
                fadeIn, stay, fadeOut
            );
        }
        return (titles == null) ? null : new TitleData(titles);
    }
}
