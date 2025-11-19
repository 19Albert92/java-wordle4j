package ru.yandex.practicum;

import ru.yandex.practicum.exeptions.WordAnalyzeException;
import ru.yandex.practicum.exeptions.WordNotFoundInDictionaryException;

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

    private final String answer;

    private int steps;

    private final WordleDictionary dictionary;

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
        this.steps = 1;
        this.answer = dictionary.getRandomWord();
    }

    private void analyzeUserWordByLength(String userWord) throws WordAnalyzeException {

        if (userWord.length() > 5) {
            throw new WordAnalyzeException("Введенное слово не должно содержать более 5 символов!");
        }

        if (userWord.length() < 5) {
            throw new WordAnalyzeException("Введенное слово не должно содержать менее 5 символов!");
        }
    }

    private void analiseUserWordByContainsInDictionary(String userWord) throws WordNotFoundInDictionaryException {

        if (!this.dictionary.foundAnswer(userWord)) {
            throw new WordNotFoundInDictionaryException("Такого слово нет в словаре!");
        }
    }

    private boolean isWinningGuess(String guess) {
        return answer.equals(guess);
    }

    public boolean makeMove(String userWord) throws WordNotFoundInDictionaryException, WordAnalyzeException {

        String normalizeUserWord;

        if (userWord.isEmpty()) {

            normalizeUserWord = generateHint();

            System.out.println(normalizeUserWord);
        } else {

            normalizeUserWord = dictionary.normalize(userWord);

            analyzeUserWordByLength(normalizeUserWord);

            analiseUserWordByContainsInDictionary(normalizeUserWord);
        }

        this.steps++;

        dictionary.createMask(normalizeUserWord, answer);

        return isWinningGuess(normalizeUserWord);
    }

    public String generateHint() {
        return this.dictionary.getHint();
    }

    public int getSteps() {
        return steps;
    }

    public void showAttemptsWithNumber() {
        System.out.printf("%n%d попытка:%n", this.steps);
    }
}
