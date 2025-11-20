package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exeptions.EmptyDictionaryException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

class WordleDictionaryLoaderTest {

    private WordleDictionaryLoader loader;

    @BeforeEach
    void setUp() {
        loader = new WordleDictionaryLoader(WordleLogger.createTestLogger());
    }

    @Test
    void loadDictionaryNotFoundFile() {
        Assertions.assertThrows(RuntimeException.class, () -> loader.loadFileDictionary("words_en.txt")
                , "Такого файла нет!");
    }

    @Test
    void loadDictionaryWordsIsNotEmpty() throws EmptyDictionaryException {

        WordleDictionary dictionary = loader.loadFileDictionary("words_ru.txt");

        List<String> wordsList = dictionary.getWords();

        assertFalse(wordsList.isEmpty(), "Список слов из полученный из фала не должен быть пустым");
    }
}