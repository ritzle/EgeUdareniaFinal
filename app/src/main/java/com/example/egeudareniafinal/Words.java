package com.example.egeudareniafinal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Words {
    static ArrayList<WordsSingleArray> array = new ArrayList<>();


    public static ArrayList<WordsSingleArray> getArray() {
        return array;
    }

    public static ArrayList<WordsSingleArray> newArray() {
        array.add(new WordsSingleArray("коры́сть", "ко́рысть"));
        array.add(new WordsSingleArray("креме́нь", "кре́мень"));
        array.add(new WordsSingleArray("лыжня́", "лы́жня"));
        array.add(new WordsSingleArray("наме́рение", "намере́ние"));
        array.add(new WordsSingleArray("наро́ст", "на́рост"));
        array.add(new WordsSingleArray("сиро́ты", "си́роты"));
        array.add(new WordsSingleArray("тамо́жня", "таможня́"));
        array.add(new WordsSingleArray("цепо́чка", "це́почка"));
        array.add(new WordsSingleArray("вероиспове́дание", "вероисповеда́ние"));
        array.add(new WordsSingleArray("новосте́й", "ново́стей"));
        array.add(new WordsSingleArray("ерети́к", "ере́тик"));
        array.add(new WordsSingleArray("бо́роду", "боро́ду"));
        array.add(new WordsSingleArray("не́друг", "недру́г"));
        array.add(new WordsSingleArray("не́нависть", "нена́висть"));
        array.add(new WordsSingleArray("но́гтя", "ногтя́"));
        array.add(new WordsSingleArray("о́трочество", "отро́чество"));
        array.add(new WordsSingleArray("свё́кла", "свё́кла"));
        array.add(new WordsSingleArray("це́нтнер", "центне́р"));
        array.add(new WordsSingleArray("то́рты", "торты́"));
        array.add(new WordsSingleArray("то́ртов", "торто́в"));
        array.add(new WordsSingleArray("по́рты", "порты́"));
        array.add(new WordsSingleArray("ко́нусы", "кону́сы"));
        array.add(new WordsSingleArray("ле́кторы", "лекто́ры"));
        array.add(new WordsSingleArray("ле́кторов", "лекто́ров"));
        return array;
    }

}


class WordsSingleArray {
    String correctWord;
    String wrongWord;

    public WordsSingleArray(String correctWord, String wrongWord) {
        this.correctWord = correctWord;
        this.wrongWord = wrongWord;
    }

    public String getCorrectWord() {
        return correctWord;
    }

    public String getWrongWord() {
        return wrongWord;
    }
}