package net.modekh.notweaks;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.modekh.notweaks.config.NoTweaksCommonConfig;
import net.modekh.notweaks.registry.CreativeTabRegistry;
import net.modekh.notweaks.registry.ItemRegistry;
import net.modekh.notweaks.registry.PacketRegistry;
import net.modekh.notweaks.registry.SoundRegistry;
import net.modekh.notweaks.utils.Reference;

@Mod(Reference.MOD_ID)
public class NoTweaks {
    public NoTweaks() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        CreativeTabRegistry.TABS.register(bus);
        ItemRegistry.ITEMS.register(bus);
        SoundRegistry.SOUNDS.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,
                NoTweaksCommonConfig.CONFIG_SPEC, "notweaks-common.toml");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        PacketRegistry.register();
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
