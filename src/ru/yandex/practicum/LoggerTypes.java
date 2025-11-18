package ru.yandex.practicum;

public enum LoggerTypes {
    INFO("info"),
    DEBUG("debug"),
    ERROR("error");

    private final String getType;

    LoggerTypes(String type) {
        this.getType = type;
    }

    public String getType() {
        return this.getType;
    }
}
