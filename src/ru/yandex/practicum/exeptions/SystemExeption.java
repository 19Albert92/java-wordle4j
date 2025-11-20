package ru.yandex.practicum.exeptions;

import ru.yandex.practicum.LoggerTypes;

public class SystemExeption extends Exception {
    public SystemExeption(String message) {
        super("[" + LoggerTypes.SYSTEM.getType() + "]: " + message);
    }
}
