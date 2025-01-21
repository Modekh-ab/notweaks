package net.modekh.notweaks.events;

import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.Interaction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.modekh.notweaks.registry.SoundRegistry;
import net.modekh.notweaks.utils.ChatUtils;
import net.modekh.notweaks.utils.PolishPhrase;
import net.modekh.notweaks.utils.Reference;
import org.apache.logging.log4j.core.util.Loader;

public class EventHandler {
    @Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    private static class ForgeBusEvents {
        private static PolishPhrase phrase = PolishPhrase.PHRASE_0;

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
    }
}
