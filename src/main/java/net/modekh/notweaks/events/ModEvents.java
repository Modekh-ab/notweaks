package net.modekh.notweaks.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.modekh.notweaks.config.NoTweaksCommonConfig;
import net.modekh.notweaks.data.PlayerTeleports;
import net.modekh.notweaks.data.providers.PlayerTeleportsProvider;
import net.modekh.notweaks.network.KeyUseC2SPacket;
import net.modekh.notweaks.network.TeleportC2SPacket;
import net.modekh.notweaks.registry.KeyRegistry;
import net.modekh.notweaks.registry.PacketRegistry;
import net.modekh.notweaks.registry.SoundRegistry;
import net.modekh.notweaks.utils.ChatUtils;
import net.modekh.notweaks.utils.PolishPhrase;
import net.modekh.notweaks.utils.Reference;

import java.util.List;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModEvents {
    private static final PolishPhrase phrase = PolishPhrase.PHRASE_0;
    private static long prevTime = 0;

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.EntityInteract event) {
        Level level = event.getLevel();
        Player player = event.getEntity();

        if (level.isClientSide && event.getHand().equals(InteractionHand.MAIN_HAND)) {
            String target = event.getTarget().getType().toShortString();
            String prefix = "entity.wildernature.";

            if (!Component.translatable(prefix + target).getString().contains(".")) {
                ChatUtils.sendPolishMessage(player, Component.literal(PolishPhrase.next(phrase).getMessage()));

                if (target.equals("hedgehog") || target.equals("raccoon")) {
                    player.playSound(target.equals("hedgehog")
                            ? SoundRegistry.POLISH_HEDGEHOG.get() : SoundRegistry.POLISH_RACCOON.get());
                } else {
                    playPolishSound(player);
                }
            } else {
                playPolishSound(player);
            }
        }
    }

    private static void playPolishSound(Player player) {
        player.playSound(SoundRegistry.POLISH_WORD.get());
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        player.getCapability(PlayerTeleportsProvider.PLAYER_TELEPORTS).ifPresent(teleports -> {
            long currentTime = System.currentTimeMillis() / 1000;

            if (currentTime - prevTime >= NoTweaksCommonConfig.COOLDOWN.get()) {
                teleports.reduceTeleports();
                prevTime = currentTime;

                player.sendSystemMessage(Component.literal("Reduced tp"));
                player.playSound(SoundRegistry.HOI.get());
            }
        });
    }

    // Exception caught during firing event:
    // Attempted to load class net/minecraft/client/Minecraft for invalid dist DEDICATED_SERVER
    @SubscribeEvent
    public static void onPlayerTeleport(EntityTeleportEvent event) {
        if (event.getEntity() instanceof Player player) {
            PacketRegistry.sendToServer(new TeleportC2SPacket());

            player.getCapability(PlayerTeleportsProvider.PLAYER_TELEPORTS).ifPresent(teleports -> {
                if (teleports.getTeleports() >= NoTweaksCommonConfig.MAX_TELEPORTS.get()) {
                    event.setCanceled(true);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onKeyUse(InputEvent.Key event) {
        if (KeyRegistry.PLAYER_TP_KEY.consumeClick()) {
            PacketRegistry.sendToServer(new KeyUseC2SPacket());
        }
    }

    // capability

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (!player.getCapability(PlayerTeleportsProvider.PLAYER_TELEPORTS).isPresent()) {
                event.addCapability(
                        new ResourceLocation(Reference.MOD_ID, "properties"), new PlayerTeleportsProvider()
                );
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            Player player = event.getOriginal();

            player.getCapability(PlayerTeleportsProvider.PLAYER_TELEPORTS).ifPresent(oldStore -> {
                player.getCapability(PlayerTeleportsProvider.PLAYER_TELEPORTS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onCapabilitiesRegister(RegisterCapabilitiesEvent event) {
        event.register(PlayerTeleports.class);
    }
}
