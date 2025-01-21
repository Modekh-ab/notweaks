package net.modekh.notweaks.utils;

import java.util.Random;

public enum PolishPhrase {
    PHRASE_0("Ja perdole!"),
    PHRASE_1("Bobr kurwa!"),
    PHRASE_2("SKUNKS"),
    PHRASE_3("Я Пьер д'Олле Скункс."),
    PHRASE_4("MOC NYGAZ");

    private final String message;

    PolishPhrase(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static PolishPhrase next(PolishPhrase phrase) {
        Random random = new Random();
        return phrase == null ? values()[0] : values()[random.nextInt(values().length)];
    }
}
