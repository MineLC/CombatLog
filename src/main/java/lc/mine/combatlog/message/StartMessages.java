package lc.mine.combatlog.message;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class StartMessages {

    public void start(final JavaPlugin plugin) {
        final File file = new File(plugin.getDataFolder(), "messages.yml");
        if (!(file.exists())) {
            plugin.saveResource("messages.yml", false);
        }
        final FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        final Set<String> keys = config.getKeys(false);
        final Map<String, String> messages = new HashMap<>();

        for (final String key : keys) {
            messages.put(key, MessageColor.color(config.get(key)));
        }
        Message.set(new Message(messages));
    }
}
