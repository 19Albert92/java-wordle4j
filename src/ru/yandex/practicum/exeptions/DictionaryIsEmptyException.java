package ru.yandex.practicum.exeptions;

public class DictionaryIsEmptyException extends RuntimeException {
    public DictionaryIsEmptyException(String message) {
        super(message);
    }
}
