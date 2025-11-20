package ru.yandex.practicum;

import ru.yandex.practicum.exeptions.EmptyDictionaryException;
import ru.yandex.practicum.exeptions.InvalidUserInputException;
import ru.yandex.practicum.exeptions.WordFormatException;
import ru.yandex.practicum.exeptions.WordNotFoundInDictionaryException;

import java.io.PrintWriter;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private final PrintWriter logger;

    private final String answer;

    private int steps;

    private final WordleDictionary dictionary;

    public WordleGame(WordleDictionary dictionary, PrintWriter logger) throws EmptyDictionaryException {
        this.dictionary = dictionary;
        this.steps = 0;
        this.answer = dictionary.getRandomWord();
        this.logger = logger;
    }

    public boolean isEnd() {
        return steps < 6;
    }

    public void showAttemptsWithNumber() {
        System.out.printf("%n%d попытка:%n", this.steps + 1);
    }

    private void analyzeUserWordByLength(String userWord) throws InvalidUserInputException {

        if (userWord.length() > 5) {
            throw new InvalidUserInputException("Введенное слово не должно содержать более 5 символов!", userWord);
        }

        if (userWord.length() < 5) {
            throw new InvalidUserInputException("Введенное слово не должно содержать менее 5 символов!", userWord);
        }
    }

    private void analyzeUserWordByFormat(String userWord) throws WordFormatException {
        if (!userWord.matches("[А-Яа-яЁё]+")) {
            throw new WordFormatException("Введенное слово должно состоять только из русских символов");
        }
    }

    private void analiseUserWordByContainsInDictionary(String userWord) throws WordNotFoundInDictionaryException {
        if (!this.dictionary.foundAnswer(userWord)) {
            throw new WordNotFoundInDictionaryException("Такого слово нет в нашем словаре!");
        }
    }

    public void checkWord(String checkedWord) throws InvalidUserInputException, WordFormatException, WordNotFoundInDictionaryException {
        analyzeUserWordByLength(checkedWord);

        analyzeUserWordByFormat(checkedWord);

        analiseUserWordByContainsInDictionary(checkedWord);
    }

    public boolean makeMove(String userWord) throws EmptyDictionaryException, WordFormatException, InvalidUserInputException, WordNotFoundInDictionaryException {

        String checkedWord;

        if (userWord.isEmpty()) {
            checkedWord = dictionary.getHint();

            System.out.println(checkedWord + " <- Подсказка компьютера ");
        } else {
            checkedWord = dictionary.normalize(userWord);

            checkWord(checkedWord);
        }

        System.out.println(dictionary.createMask(checkedWord, answer));

        return isWinningGuess(checkedWord);
    }

    public void nextStep() {
        steps++;
    }

    private boolean isWinningGuess(String guess) {
        return answer.equals(guess);
    }

    public String getAnswer() {
        return answer;
    }
}
