package ru.yandex.practicum;

import ru.yandex.practicum.exeptions.EmptyDictionaryException;
import ru.yandex.practicum.exeptions.WordNotFoundInDictionaryException;

import java.io.PrintWriter;
import java.util.*;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private final PrintWriter logger;

    private final Random random = new Random();

    private final List<String> words;

    private final LinkedHashMap<String, List<String>> enteredOptions;

    private final Map<Integer, Character> charactersPosition;

    private final Set<Character> charactersForbidden;

    private final Set<Character> charactersRequired;

    public WordleDictionary(PrintWriter logger, List<String> words) {
        this.logger = logger;

        this.words = words;

        this.enteredOptions = new LinkedHashMap<>();

        this.charactersPosition =  new HashMap<>();

        this.charactersForbidden = new HashSet<>();

        this.charactersRequired = new HashSet<>();
    }

    public String normalize(String word) {
        return word.toLowerCase().trim().replace("ё", "е");
    }

    public boolean foundAnswer(String word) {
        return words.contains(word);
    }

    public String getHint() throws EmptyDictionaryException {
        if (enteredOptions.isEmpty()) {
            return getRandomWord();
        }

        List<String> tipsList = enteredOptions.lastEntry().getValue();

        if (tipsList.isEmpty()) {
            logger.println("Словарь пуст");
            throw new RuntimeException("Словарь пуст");
        }

        return tipsList.get(random.nextInt(tipsList.size()));
    }

    public String getRandomWord() throws EmptyDictionaryException {
        if (words.isEmpty()) {
            logger.println("Hint list is empty → EmptyDictionaryException");
            throw new EmptyDictionaryException("Dictionary is empty");
        }
        return this.words.get(random.nextInt(this.words.size()));
    }

    public String createMask(String guess, String answer) throws EmptyDictionaryException {

        StringBuilder sb = new StringBuilder();

        char[] guessToArrayChar = guess.toCharArray();

        for (int i = 0; i < guessToArrayChar.length; i++) {

            char guessChar = guessToArrayChar[i];

            if (guessChar == answer.charAt(i)) {
                sb.append(MaskCharacterPlaceholder.OK_LETTERS.getCharLetter());
                charactersPosition.put(i, guessChar);
                charactersRequired.add(guessChar);
            } else if (guessChar != answer.charAt(i) && answer.indexOf(guessChar) >= 0) {
                sb.append(MaskCharacterPlaceholder.MAYBE_LETTERS.getCharLetter());
                charactersRequired.add(guessChar);
            } else if (guessChar != answer.charAt(i) && answer.indexOf(guessChar) == -1) {
                sb.append(MaskCharacterPlaceholder.SKIP_LETTERS.getCharLetter());
                charactersForbidden.add(guessChar);
            }
        }

        updateFilteredWords(guess);

        return sb.toString();
    }

    private void updateFilteredWords(String guessWord) throws EmptyDictionaryException {
        List<String> getWordsByKey;

        if (enteredOptions.isEmpty()) {
            getWordsByKey = words;
        } else {
            getWordsByKey = enteredOptions.lastEntry().getValue();
        }

        enteredOptions.put(guessWord, filterWords(getWordsByKey));
    }

    private boolean isForbidden(String word, Set<Character> characters) {
        for (char guessChar : word.toCharArray()) {
            if (characters.contains(guessChar)) {
                return false;
            }
        }

        return true;
    }

    private boolean isRequired(String word, Set<Character> characters) {
        for (char requiredChar : characters) {
            if (!word.contains(String.valueOf(requiredChar))) {
                return false;
            }
        }

        return true;
    }

    private boolean isPosition(String word, Map<Integer, Character> charactersPosition) {
        for (Map.Entry<Integer, Character> entry : charactersPosition.entrySet()) {
            int requiredChar = entry.getKey();
            char position = entry.getValue();

            if (word.charAt(requiredChar) != position) {
                return false;
            }
        }

        return true;
    }

    private List<String> filterWords(List<String> lastFilteredWords) throws EmptyDictionaryException {
        ArrayList<String> filteredWords = new ArrayList<>();

        for (String word : lastFilteredWords) {

            if (!isForbidden(word, charactersForbidden)) {
                continue;
            }

            if (!isRequired(word, charactersRequired)) {
                continue;
            }

            if (!isPosition(word, charactersPosition)) {
                continue;
            }

            filteredWords.add(word);
        }

        if (filteredWords.isEmpty()) {
            throw new EmptyDictionaryException("Словарь пуст!");
        }

//        charactersForbidden.clear(); <-- убрал для тестов
//        charactersRequired.clear(); <-- убрал для тестов

        return filteredWords;
    }

    public Set<Character> getCharactersRequired() {
        return charactersRequired;
    }

    public Set<Character> getCharactersForbidden() {
        return charactersForbidden;
    }

    public Map<Integer, Character> getCharactersPosition() {
        return charactersPosition;
    }

    public LinkedHashMap<String, List<String>> getEnteredOptions() {
        return enteredOptions;
    }

    public List<String> getWords() {
        return words;
    }
}
