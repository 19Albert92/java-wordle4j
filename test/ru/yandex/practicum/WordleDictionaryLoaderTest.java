package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exeptions.WorldFileException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryLoaderTest {

    private final WordleDictionaryLoader loader = new WordleDictionaryLoader();

    @Test
    void loadDictionaryNotFoundFile() {
        Assertions.assertThrows(WorldFileException.class, () -> loader.load("words_en.txt")
                , "Такого файла нет!");
    }

    @Test
    void loadDictionaryWordsIsNotEmpty() {

        WordleDictionary dictionary = loader.load("words_ru.txt");

        List<String> wordsList = dictionary.getWords();

        assertFalse(wordsList.isEmpty(), "Список слов из полученный из фала не должен быть пустым");
    }

}