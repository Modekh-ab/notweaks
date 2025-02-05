package net.modekh.notweaks.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import net.modekh.notweaks.config.NoTweaksCommonConfig;
import net.modekh.notweaks.data.providers.PlayerTeleportsProvider;
import net.modekh.notweaks.utils.ChatUtils;

import java.util.List;
import java.util.function.Supplier;

public class KeyUseC2SPacket {
    public KeyUseC2SPacket() {
    }

    public KeyUseC2SPacket(FriendlyByteBuf buf) {
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

            ServerLevel serverLevel = context.getSender().serverLevel();

            List<ServerPlayer> players = serverLevel.getPlayers(serverPlayer -> true);

            if (players.size() == 2) {
                Player otherPlayer = serverLevel.getNearestPlayer(player, 0.0F);

                if (otherPlayer != null) {
                    player.moveTo(otherPlayer.getX(), otherPlayer.getY(), otherPlayer.getZ());
                }
            } else {
                String prefix = "message.notweaks.player_tp_";
                player.displayClientMessage(Component.translatable(players.size() < 2
                        ? prefix + "alone"
                        : prefix + "limit"), true);
            }
        });

        return true;
    }
}
