package ru.yandex.practicum;

import ru.yandex.practicum.exeptions.EmptyDictionaryException;
import ru.yandex.practicum.exeptions.InvalidUserInputException;
import ru.yandex.practicum.exeptions.WordFormatException;
import ru.yandex.practicum.exeptions.WordNotFoundInDictionaryException;

import java.io.PrintWriter;
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

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try(PrintWriter logger = WordleLogger.createFileLogger("log.txt")) {

            WordleDictionaryLoader loader = new WordleDictionaryLoader(logger);

            WordleGame game = new WordleGame(loader.loadFileDictionary("words_ru.txt"), logger);

            runGame(game, logger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void runGame(WordleGame game, PrintWriter logger) {

        showGreetingText();

        while(game.isEnd()) {

            try {
                game.showAttemptsWithNumber();

                String userWord = scanner.nextLine();

                if (game.makeMove(userWord)) {
                    System.out.println("\nВы выйграли!");
                    return;
                }

                game.nextStep();

            } catch (WordFormatException | InvalidUserInputException | WordNotFoundInDictionaryException | EmptyDictionaryException e) {
                logger.println(e.getMessage());
                System.out.println(e.getMessage());
            }
        }

        System.out.println("\nВы проиграли! Загаданное слово -> " + game.getAnswer());
    }

    private static void showGreetingText() {
        System.out.println("Привет! Компьютер загадал текст.");
        System.out.println("Попробуй отгадай его.(п.c у тебя есть 6 попыток!).");
    }

}
