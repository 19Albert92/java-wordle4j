package ru.yandex.practicum;

import ru.yandex.practicum.exeptions.DictionaryEmptyException;
import ru.yandex.practicum.exeptions.DictionaryIsEmptyException;

import java.util.*;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private final Random random = new Random();

    private final List<String> words;

    private final LinkedHashMap<String, List<String>> enteredOptions;

    private final Map<Character, Integer> charactersPosition;

    private final Set<Character> charactersForbidden;

    private final Set<Character> charactersRequired;

    public WordleDictionary(List<String> words) {

        this.words = words;

        enteredOptions = new LinkedHashMap<>();

        charactersPosition =  new HashMap<>();

        charactersForbidden = new HashSet<>();

        charactersRequired = new HashSet<>();
    }

    public boolean foundAnswer(String word) {
        return words.contains(word);
    }

    public String normalize(String word) {
        return word.toLowerCase().trim().replace("ё", "е");
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

    private boolean isPosition(String word, Map<Character, Integer> charactersPosition) {
        for (Map.Entry<Character, Integer> entry : charactersPosition.entrySet()) {
            char requiredChar = entry.getKey();
            int position = entry.getValue();

            if (word.charAt(position) != requiredChar) {
                return false;
            }
        }

        return true;
    }

    private List<String> filterWords(List<String> lastFilteredWords) {
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
            throw new DictionaryEmptyException("Словарь пуст!");
        }

        return filteredWords;
    }

    private void updateFilteredWords(String guessWord) {
        List<String> getWordsByKey;

        if (enteredOptions.isEmpty()) {
            getWordsByKey = words;
        } else {
            getWordsByKey = enteredOptions.lastEntry().getValue();
        }

        enteredOptions.put(guessWord, filterWords(getWordsByKey));
    }

    public String createMask(String guess, String answer) {

        StringBuilder sb = new StringBuilder();

        char[] guessToArrayChar = guess.toCharArray();

        for (int i = 0; i < guessToArrayChar.length; i++) {

            char guessChar = guessToArrayChar[i];

            if (guessChar == answer.charAt(i)) {
                sb.append("+");
                charactersPosition.put(guessChar, i);
                charactersRequired.add(guessChar);
            } else if (guessChar != answer.charAt(i) && answer.indexOf(guessChar) >= 0) {
                sb.append("^");
                charactersRequired.add(guessChar);
            } else if (guessChar != answer.charAt(i) && answer.indexOf(guessChar) == -1) {
                sb.append("-");
                charactersForbidden.add(guessChar);
            }
        }

        updateFilteredWords(guess);

        return sb.toString();
    }

    public LinkedHashMap<String, List<String>> getEnteredOptions() {
        return enteredOptions;
    }

    public String getHint() {
        if (enteredOptions.isEmpty()) {
            return getRandomWord();
        }

        List<String> tipsList = enteredOptions.lastEntry().getValue();
        if (tipsList.isEmpty()) {
            throw new DictionaryEmptyException("Dictionary is empty");
        }
        return tipsList.get(random.nextInt(tipsList.size()));
    }

    public String getRandomWord() throws DictionaryIsEmptyException {
        if (words.isEmpty()) {
            throw new DictionaryIsEmptyException("Dictionary is empty");
        }
        return this.words.get(random.nextInt(this.words.size()));
    }

    public List<String> getWords() {
        return words;
    }

    public Map<Character, Integer> getCharactersPosition() {
        return charactersPosition;
    }

    public Set<Character> getCharactersForbidden() {
        return charactersForbidden;
    }

    public Set<Character> getCharactersRequired() {
        return charactersRequired;
    }
}
