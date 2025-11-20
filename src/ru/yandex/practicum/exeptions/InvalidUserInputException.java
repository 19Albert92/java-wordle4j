package ru.yandex.practicum.exeptions;

public class InvalidUserInputException extends GameException {

    private final String word;

    public InvalidUserInputException(String message, String word) {
        super(message);
        this.word = word;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " ваше слово -> " + word;
    }
}
