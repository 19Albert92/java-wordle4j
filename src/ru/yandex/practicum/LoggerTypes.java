package ru.yandex.practicum;

public enum LoggerTypes {

    GAMING("gaming"),
    SYSTEM("system");

    private final String getType;

    LoggerTypes(String type) {
        this.getType = type;
    }

    public String getType() {
        return this.getType;
    }
}
