package ru.yandex.practicum;

import ru.yandex.practicum.exeptions.WorldFileException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

    public WordleDictionary load(String path, Charset charset) throws WorldFileException {

        List<String> words = new ArrayList<>();

        Path dictionaryPath = Path.of(path);

        try (BufferedReader br = new BufferedReader(new FileReader(dictionaryPath.toString(), charset))) {

            while(br.ready()) {
                String word = br.readLine();
                if (word.length() == 5) {
                    words.add(word);
                }
            }

            return new WordleDictionary(words);

        } catch (IOException exception) {
            throw new WorldFileException(exception.getMessage());
        }
    }

    public WordleDictionary load(String path) {
        return load(path, StandardCharsets.UTF_8);
    }

}
