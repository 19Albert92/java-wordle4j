package ru.yandex.practicum;

public enum MaskCharacterPlaceholder {

    SKIP_LETTERS('-'),
    MAYBE_LETTERS('^'),
    OK_LETTERS('+');

    public final char character;

    MaskCharacterPlaceholder(char character) {
        this.character = character;
    }

    public char getCharLetter() {
        return character;
    }
}
