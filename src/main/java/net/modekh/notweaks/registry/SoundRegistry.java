package net.modekh.notweaks.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.modekh.notweaks.utils.Reference;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS
            = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Reference.MOD_ID);

    public static final RegistryObject<SoundEvent> POLISH_WORD =
            registerSoundEvents("polish_word");
    public static final RegistryObject<SoundEvent> POLISH_HEDGEHOG =
            registerSoundEvents("polish_hedgehog");
    public static final RegistryObject<SoundEvent> POLISH_RACCOON =
            registerSoundEvents("polish_raccoon");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUNDS.register(name,
                () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Reference.MOD_ID, name)));
    }
}
