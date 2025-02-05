package net.modekh.notweaks.data.providers;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.modekh.notweaks.data.PlayerTeleports;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerTeleportsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerTeleports> PLAYER_TELEPORTS =
            CapabilityManager.get(new CapabilityToken<>() {});

    private PlayerTeleports teleports = null;
    private final LazyOptional<PlayerTeleports> optional = LazyOptional.of(this::createPlayerTeleports);

    private @NotNull PlayerTeleports createPlayerTeleports() {
        if (this.teleports == null) {
            this.teleports = new PlayerTeleports();
        }

        return this.teleports;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == PLAYER_TELEPORTS) {
            return this.optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        createPlayerTeleports().saveNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        createPlayerTeleports().loadNBT(tag);
    }
}
