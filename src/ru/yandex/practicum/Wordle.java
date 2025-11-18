package ru.yandex.practicum;

import ru.yandex.practicum.exeptions.WordAnalyzeException;
import ru.yandex.practicum.exeptions.WordNotFoundInDictionaryException;

import java.util.Scanner;

/*
    в главном классе нам нужно:
        создать лог-файл (он должен передаваться во все классы)
        создать загрузчик словарей WordleDictionaryLoader
        загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
        затем создать игру WordleGame и передать ей словарь
        вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
        вывести состояние игры и конечный результат
 */
public class Wordle {

    private static final WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader();

    private static final WordleDictionary dictionary = wordleDictionaryLoader.load("words_ru.txt");

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        WordleGame wordleGame = new WordleGame(dictionary);

        showGreetingText();

        try {
            runGameLoop(wordleGame);
        } catch (Exception exception) {
            WordleLogger.log(LoggerTypes.ERROR, exception.getMessage());
        }

    }

    private static void showGreetingText() {
        System.out.println("Привет! Компьютер загадал текст.");
        System.out.println("Попробуй отгадай его.(п.c у тебя есть 6 попыток!).");
    }

    public static void runGameLoop(WordleGame wordleGame) {

        while (wordleGame.getSteps() <= 6) {

            wordleGame.showAttemptsWithNumber();

            try {
                String userWord = scanner.nextLine();
                if (wordleGame.makeMove(userWord)) {
                    System.out.println("\nВы выйграли!");
                    return;
                }
            } catch (WordNotFoundInDictionaryException | WordAnalyzeException exception) {
                System.out.println(exception.getMessage());
            }
        }

        System.out.println("\nВы проиграли!");
    }
}
