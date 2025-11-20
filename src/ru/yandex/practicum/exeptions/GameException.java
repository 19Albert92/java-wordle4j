package ru.yandex.practicum.exeptions;

import ru.yandex.practicum.LoggerTypes;

public class GameException extends Exception {
    public GameException(String message) {
        super("[" + LoggerTypes.GAMING.getType() + "]: " + message);
    }
}
