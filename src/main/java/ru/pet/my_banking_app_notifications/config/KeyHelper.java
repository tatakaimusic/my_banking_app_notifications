package ru.pet.my_banking_app_notifications.config;

import java.util.Objects;

public class KeyHelper {

    private final static String defaultPrefix = "banking";
    private static String prefix = null;

    public static void setPrefix(String keyPrefix) {
        prefix = keyPrefix;
    }

    public static String getKey(String key) {
        return getPrefix() + ":" + key;
    }

    private static String getPrefix() {
        return Objects.requireNonNullElse(prefix, defaultPrefix);
    }

}
