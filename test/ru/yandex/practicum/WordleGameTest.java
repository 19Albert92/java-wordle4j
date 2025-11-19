package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exeptions.WordAnalyzeException;
import ru.yandex.practicum.exeptions.WordNotFoundInDictionaryException;

import java.util.List;

class WordleGameTest {

    private WordleGame game;

    @BeforeEach
    void setUp() {
        WordleDictionary dictionary = new WordleDictionary(List.of("бекар", "авизо", "бейка"));
        game = new WordleGame(dictionary);
    }


    @Test
    void testShouldThrowWhenWordIsLongerThanFiveCharacters() {

        String guess = "бекаре";

        Assertions.assertThrows(WordAnalyzeException.class, () -> game.makeMove(guess),
                "Должна вернуться ошибка так как слово состоит больше чем из 5 букв");
    }

    @Test
    void testShouldThrowWhenWordIsShorterThanFiveCharacters() {

        String guess = "бек";

        Assertions.assertThrows(WordAnalyzeException.class, () -> game.makeMove(guess),
                "Должна вернуться ошибка так как слово состоит меньше чем из 5 букв");
    }

    @Test
    void testShouldThrowWhenWordNotInDictionary() {
        Assertions.assertThrows(WordNotFoundInDictionaryException.class, () -> game.makeMove("apple"),
                "Должна вернуться ошибка так как такого слово нет в словаре");
    }

    @Test
    void testShouldReturnEqualsNumberOfSteps() throws WordNotFoundInDictionaryException, WordAnalyzeException {

        String guess = "бекар";

        game.makeMove(guess);

        System.out.println(game.getSteps());

        Assertions.assertEquals(2, game.getSteps(),
                "Должно было вернуть 2");
    }
}