package lc.mine.combatlog.message;

import java.util.List;

import org.bukkit.ChatColor;

public final class MessageColor {

    public static String color(final Object object) {
        if (object == null) {
            return null;
        }
        String message = object.toString();

        if (object instanceof List<?>) {
            final List<?> list = (List<?>)object;
            final StringBuilder builder = new StringBuilder();
            int size = list.size();
            int i = 0;
            for (final Object object2 : list) {
                builder.append(object2);
                if (++i == size) {
                    continue;
                }
                builder.append('\n');
            }
            message = builder.toString();
        }

        if (message.isEmpty() || message.isBlank()) {
            return null;
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}