package com.example.egeudareniafinal;

public class WordsShortData {

    private final String correctWord;
    private final String wrongWord;
    private final boolean correct = false;

    public WordsShortData(String correctWord, String wrongWord) {
        this.correctWord = correctWord;
        this.wrongWord = wrongWord;
    }

    public String getCorrectWord() {
        return correctWord;
    }

    public String getWrongWord() {
        return wrongWord;
    }

    public boolean isCorrect() {
        return correct;
    }
}
