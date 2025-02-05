package net.modekh.notweaks.data;

import net.minecraft.nbt.CompoundTag;
import net.modekh.notweaks.config.NoTweaksCommonConfig;

public class PlayerTeleports {
    private int teleports;
    private final int MIN_TELEPORTS = 0;
    private final int MAX_TELEPORTS = NoTweaksCommonConfig.MAX_TELEPORTS.get();

    public int getTeleports() {
        return teleports;
    }

    public void addTeleports() {
        this.teleports = Math.min(teleports + 1, MAX_TELEPORTS);
    }

    public void reduceTeleports() {
        this.teleports = Math.max(teleports - 1, MIN_TELEPORTS);
    }

    public void copyFrom(PlayerTeleports source) {
        this.teleports = source.teleports;
    }

    public void saveNBT(CompoundTag tag) {
        tag.putInt("teleports", teleports);
    }

    public void loadNBT(CompoundTag tag) {
        teleports = tag.getInt("teleports");
    }
}
