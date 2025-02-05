package net.modekh.notweaks.registry;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.modekh.notweaks.utils.Reference;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeyRegistry {
    public static final String CATEGORY = "NoTweaks";

    public static final KeyMapping PLAYER_TP_KEY = new KeyMapping("key.notweaks.tp_player", GLFW.GLFW_KEY_KP_5, CATEGORY);

    @SubscribeEvent
    public static void onKeybindingRegistry(RegisterKeyMappingsEvent event) {
        event.register(PLAYER_TP_KEY);
    }
}
