package net.modekh.notweaks.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class NoTweaksCommonConfig {
    public static final ForgeConfigSpec CONFIG_SPEC;

    public static final ForgeConfigSpec.IntValue MAX_TELEPORTS;
    public static final ForgeConfigSpec.LongValue COOLDOWN;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("NoTweaks Config");

        // defining configs
        MAX_TELEPORTS = builder
                .comment("Max value of teleports for a player")
                .defineInRange("maxTeleportsAmount", 17, 1, 99);
        COOLDOWN = builder
                .comment("Cooldown for teleports (sec)")
                .defineInRange("cooldown", 993, 1, Long.MAX_VALUE);

        builder.pop();
        CONFIG_SPEC = builder.build();
    }
}
