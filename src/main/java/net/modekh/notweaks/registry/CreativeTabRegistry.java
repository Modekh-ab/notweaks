package net.modekh.notweaks.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.modekh.notweaks.utils.Reference;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Reference.MOD_ID);

    public static final List<Supplier<? extends ItemLike>> NOTWEAKS_TAB_ITEMS = new ArrayList<>();

    public static final RegistryObject<CreativeModeTab> NOTWEAKS_TAB = TABS.register("notweaks_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.notweaks_tab"))
                    .icon(ItemRegistry.WEATHER_BELL.get()::getDefaultInstance)
                    .displayItems((displayParams, output) ->
                            NOTWEAKS_TAB_ITEMS.forEach(itemLike -> output.accept(itemLike.get())))
                    .withSearchBar()
                    .build()
    );

    public static <T extends Item> RegistryObject<T> addToTab(RegistryObject<T> itemLike) {
        NOTWEAKS_TAB_ITEMS.add(itemLike);
        return itemLike;
    }

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
//        if (event.getTab() == NOTWEAKS_TAB.get()) {
//            event.accept(Items.CROSSBOW);
//        }
    }
}
