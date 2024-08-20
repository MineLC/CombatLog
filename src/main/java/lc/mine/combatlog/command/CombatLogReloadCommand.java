package lc.mine.combatlog.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import lc.mine.combatlog.CombatLogPlugin;

public final class CombatLogReloadCommand implements CommandExecutor {

    private final CombatLogPlugin plugin;

    public CombatLogReloadCommand(CombatLogPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        plugin.reloadConfig();
        plugin.load();
        sender.sendMessage("Plugin reloaded!");
        return true;
    }
}
