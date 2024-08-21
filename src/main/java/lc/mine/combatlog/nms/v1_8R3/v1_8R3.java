package lc.mine.combatlog.nms.v1_8R3;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

public final class v1_8R3 {
    public static void sendTitle(final Player player, final String title, final boolean subtitle) {
        final IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + title + "\"}");

        final PacketPlayOutTitle packettitle = new PacketPlayOutTitle((subtitle) ? EnumTitleAction.SUBTITLE : EnumTitleAction.TITLE, chatTitle);
        final PacketPlayOutTitle length = new PacketPlayOutTitle(1, 60, 5);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packettitle);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
    }
}
