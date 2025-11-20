package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exeptions.EmptyDictionaryException;
import ru.yandex.practicum.exeptions.InvalidUserInputException;
import ru.yandex.practicum.exeptions.WordFormatException;
import ru.yandex.practicum.exeptions.WordNotFoundInDictionaryException;

import java.io.PrintWriter;
import java.util.List;

class WordleGameTest {

    private WordleGame game;

    @BeforeEach
    void setUp() throws EmptyDictionaryException {
        PrintWriter logger = WordleLogger.createTestLogger();

        WordleDictionary dictionary = new WordleDictionary(logger, List.of("бекар", "авизо", "бейка"));
        game = new WordleGame(dictionary, logger);
    }


    @Test
    void testShouldThrowWhenWordIsLongerThanFiveCharacters() {

        String guess = "бекаре";

        Assertions.assertThrows(InvalidUserInputException.class, () -> game.checkWord(guess),
                "Должна вернуться ошибка так как слово состоит больше чем из 5 букв");
    }

    @Test
    void testShouldThrowWhenWordIsShorterThanFiveCharacters() {

        String guess = "бек";

        Assertions.assertThrows(InvalidUserInputException.class, () -> game.checkWord(guess),
                "Должна вернуться ошибка так как слово состоит меньше чем из 5 букв");
    }

    @Test
    void testShouldThrowWhenWordNotInDictionary() {
        String guess = "бекаа";

        Assertions.assertThrows(WordNotFoundInDictionaryException.class, () -> game.checkWord(guess),
                "Должна вернуться ошибка так как такого слово нет в словаре");
    }

    @Test
    void testShouldThrowWhenWordNotValidCharacter() {
        Assertions.assertThrows(WordFormatException.class, () -> game.makeMove("apple"),
                "Должна вернуться ошибка так как такого слово нет в словаре");
    }
}