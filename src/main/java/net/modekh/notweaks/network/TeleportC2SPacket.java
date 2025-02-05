package net.modekh.notweaks.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.modekh.notweaks.config.NoTweaksCommonConfig;
import net.modekh.notweaks.data.providers.PlayerTeleportsProvider;
import net.modekh.notweaks.utils.ChatUtils;

import java.util.function.Supplier;

public class TeleportC2SPacket {
    private static final String MESSAGE_TELEPORT = "message.notweaks.teleport";
    private static final String MESSAGE_TELEPORT_LIMIT = "message.notweaks.teleport_limit";

    public TeleportC2SPacket() {
    }

    public TeleportC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            if (player == null) {
                return;
            }

            player.getCapability(PlayerTeleportsProvider.PLAYER_TELEPORTS).ifPresent(teleports -> {
                int currentTeleports = teleports.getTeleports();
                int maxTeleports = NoTweaksCommonConfig.MAX_TELEPORTS.get();

                if (currentTeleports < maxTeleports) {
                    ChatUtils.sendMessage(player, ChatUtils.sender("Server"),
                            Component.translatable(MESSAGE_TELEPORT, String.valueOf(maxTeleports - currentTeleports - 1)),
                            false);
                    teleports.addTeleports();
                } else {
                    ChatUtils.sendMessage(player, ChatUtils.sender("Server"),
                            Component.translatable(MESSAGE_TELEPORT_LIMIT), false);
                }
            });
        });

        return true;
    }
}
