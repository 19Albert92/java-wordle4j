package ru.yandex.practicum.exeptions;

public class DictionaryEmptyException extends RuntimeException {
    public DictionaryEmptyException(String message) {
        super(message);
    }
}
