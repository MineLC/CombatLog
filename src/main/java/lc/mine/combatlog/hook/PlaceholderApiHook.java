package lc.mine.combatlog.hook;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import lc.mine.combatlog.storage.Options;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceholderApiHook extends PlaceholderExpansion {
    private final Options options;

    public PlaceholderApiHook(Options options) {
        this.options = options;
    }

    @Override
    public @NotNull String getAuthor() {
        return "MineLC";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "combat";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.0.1";
    }
    
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        switch (params) {
            case "kdr":
                final int kills = player.getStatistic(Statistic.PLAYER_KILLS);
                final int deaths = player.getStatistic(Statistic.DEATHS);
                return (deaths == 0) ? String.valueOf(kills) : String.format("%.2f", ((double)kills / (double)deaths));

            case "level":
                return String.valueOf(player.getStatistic(Statistic.PLAYER_KILLS) / options.getKillsToLevelup());
            default:
                break;
        }
        return null;
    }

}
