package ru.yandex.practicum.exeptions;

public class WordNotFoundInDictionaryException extends Exception {
    public WordNotFoundInDictionaryException() {
    }
    public WordNotFoundInDictionaryException(String message) {
        super(message);
    }
}
