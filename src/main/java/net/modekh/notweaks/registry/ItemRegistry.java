package net.modekh.notweaks.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.modekh.notweaks.items.WeatherBellItem;
import net.modekh.notweaks.utils.Reference;

import static net.modekh.notweaks.registry.CreativeTabRegistry.addToTab;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS
            = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);

    public static final RegistryObject<Item> WEATHER_BELL =
            addToTab(ITEMS.register("weather_bell", WeatherBellItem::new));
}
