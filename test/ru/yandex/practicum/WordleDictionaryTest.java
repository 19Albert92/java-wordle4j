package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exeptions.DictionaryEmptyException;

import java.util.*;

class WordleDictionaryTest {

    private WordleDictionary dictionary;

    private String answer;

    private List<String> words;

    @BeforeEach
    void setUp() {
        dictionary = new WordleDictionary(List.of("бекар", "авизо", "бейка", "авгит", "авгур", "аверс" ,"бердо", "вафля"));
        answer = "авгит";
        words = dictionary.getWords();
    }

    @Test
    void testShouldLowercaseTrimAndReplaceChar() {
        String word = dictionary.normalize(" ПрИвЁт ");

        Assertions.assertEquals("привет", word,
                "Функция нормальзирования должна была вернуть 'привет'");
    }

    @Test
    void testShouldReturnFalseWhenWordsListEmpty() {
        Assertions.assertFalse(words.isEmpty(), "Список не должен быть пустым");
    }

    @Test
    void testShouldReturnTrueWhenAnswerContainsFromWordsList() {

        String answerRandomHint = dictionary.getRandomWord();

        List<String> words = dictionary.getWords();

        Assertions.assertTrue(words.contains(answerRandomHint), "Загаданное слово есть в словаре!");
    }

    @Test
    void testShouldReturnFalseWhenAnswerContainsFromWordsList() {
        String guessAnswer = "привет";
        Assertions.assertFalse(words.contains(guessAnswer), "Загаданное слово нет в словаре!");
    }

    @Test
    void testShouldReturnMaskPlusWhenCharacterInCorrectPosition() {
        String guest = "авгит";

        String returnedMask = dictionary.createMask(guest, answer);

        Assertions.assertEquals("+++++", returnedMask,
                "Маска должна была вернуть '+++++' а вернула " + returnedMask);
    }

    @Test
    void testShouldReturnMaskCaretWhenCharacterExistsButWrongPosition() {

        String guest = "бердо";

        String returnedMask = dictionary.createMask(guest, answer);

        Assertions.assertEquals("-----", dictionary.createMask(guest, answer),
                "Маска должна была вернуть '-----' а вернула " + returnedMask);
    }

    @Test
    void testShouldReturnMaskMinusWhenCharacterNotInAnswer() {

        String guest = "серпе";

        String returnedMask = dictionary.createMask(guest, answer);

        Assertions.assertEquals("-----", dictionary.createMask(guest, answer),
                "Маска должна была вернуть '-----' а вернула " + returnedMask);
    }

    @Test
    void testShouldReturnMaskMixedWhenGuessContainsMixedCharacters() {
        String guest = "авгуи";

        String returnedMask = dictionary.createMask(guest, answer);

        Assertions.assertEquals("+++-^", dictionary.createMask(guest, answer),
                "Маска должна была вернуть '+++-^' а вернула " + returnedMask);
    }

    @Test
    void testShouldReturnTrueWhenWordContainsRequiredCharacters() {

        String guest = "авгоп";

        dictionary.createMask(guest, answer);

        Set<Character> charactersRequired = dictionary.getCharactersRequired();

        Assertions.assertEquals(3, charactersRequired.size(),
                "В данном слове емеются 3 обязательные буквы");

        Assertions.assertArrayEquals(new Character[]{'а', 'в', 'г'}, charactersRequired.toArray(),
                "В данном слове не должгы быть буквы 'а в г'");
    }

    @Test
    void testShouldReturnTrueWhenWordContainsForbiddenCharacters() {

        String guest = "авгоп";

        dictionary.createMask(guest, answer);

        Set<Character> charactersForbidden = dictionary.getCharactersForbidden();

        Assertions.assertEquals(2, charactersForbidden.size(),
                "В данном слове емеются 2 запрещенные буквы");

        Assertions.assertArrayEquals(new Character[]{'о', 'п'}, charactersForbidden.toArray(),
                "В данном слове не должгы быть буквы 'о п'");
    }

    @Test
    void testShouldReturnFalseWhenWordDoesMatchRequiredPositions() {

        String guest = "авгоп";

        dictionary.createMask(guest, answer);

        Map<Integer, Character> charactersPosition = dictionary.getCharactersPosition();

        Assertions.assertNotEquals('a', (int) charactersPosition.get(1),
                "Данное слово не подходит по буквам и позициям на них");
    }

    @Test
    void testShouldUpdateCharactersForbiddenWhenLetterPresent() {

        String guest = "авежщ";

        dictionary.createMask(guest, answer);

        Set<Character> charactersForbidden = dictionary.getCharactersForbidden();

        Assertions.assertEquals(3, charactersForbidden.size(),
                "В запрещенные слова должны были добавить 3 буквы 'е ж щ'");
    }

    @Test
    void testShouldUpdateCharactersRequiredWhenLetterPresent() {
        String guest = "авежщ";

        dictionary.createMask(guest, answer);

        Set<Character> charactersRequired = dictionary.getCharactersRequired();

        Assertions.assertEquals(2, charactersRequired.size(),
                "В обязательные слова должны были добавить 2 буквы 'а в'");
    }

    @Test
    void testUpdateWordsShouldAddNewEntryToEnteredOptions() {
        String guest = "авежщ";

        dictionary.createMask(guest, answer);

        LinkedHashMap<String, List<String>> enteredOptions = dictionary.getEnteredOptions();

        Assertions.assertEquals(1, enteredOptions.size(),
                "Должно было добавиться запись с о списком подсказок");

        String[] hintsArray = new String[] {"авизо", "авгит", "авгур"};

        Assertions.assertArrayEquals(hintsArray, enteredOptions.lastEntry().getValue().toArray(),
                "Должно было добавить 3 слово в список подсказок");
    }

    @Test
    void testShouldReturnWordFromListWhenListIsEmpty() {

        Assertions.assertNotNull(dictionary.getHint(),
                "Подсказка должна возвращаться всегда");

        Assertions.assertDoesNotThrow(() -> dictionary.getHint(),
                "При первой подсказке не должно быть ошибок");
    }
}