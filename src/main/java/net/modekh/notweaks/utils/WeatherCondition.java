package net.modekh.notweaks.utils;

import net.minecraft.network.chat.Component;

public enum WeatherCondition {
    CLEAR(false, false, "clear"),
    RAIN(true, false, "rain"),
    THUNDER(true, true, "thunder");

    private final boolean rain;
    private final boolean thunder;
    private final String message;

    WeatherCondition(boolean rain, boolean thunder, String message) {
        this.rain = rain;
        this.thunder = thunder;
        this.message = message;
    }

    public boolean getRaining() {
        return rain;
    }

    public boolean getThundering() {
        return thunder;
    }

    public String getMessage() {
        return Component.translatable("message.notweaks.weather_bell_" + message).getString();
    }

    public WeatherCondition next() {
        int nextOrd = this.ordinal() + 1;
        return nextOrd < values().length ? values()[nextOrd] : values()[0];
    }
}
