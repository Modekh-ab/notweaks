package net.modekh.notweaks.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

public class ChatUtils {
    public static MutableComponent sender(String sender) {
        return Component.literal("[").withStyle(ChatFormatting.DARK_GREEN)
                .append(Component.literal(sender).withStyle(ChatFormatting.GREEN))
                .append(Component.literal("]").withStyle(ChatFormatting.DARK_GREEN));
    }

    public static MutableComponent polishSender() {
        return sender(Component.translatable("message.notweaks.skunks").getString());
    }

    public static void sendMessage(Player player, MutableComponent sender, MutableComponent message, boolean bar) {
        player.displayClientMessage(sender.append(" ").append(message.withStyle(ChatFormatting.WHITE)), bar);
    }

    public static void sendPlainMessage(Player player, MutableComponent message, boolean bar) {
        player.displayClientMessage(message.withStyle(ChatFormatting.WHITE), bar);
    }

    public static void sendPolishMessage(Player player, MutableComponent message) {
        sendMessage(player, polishSender(), message, true);
    }
}
