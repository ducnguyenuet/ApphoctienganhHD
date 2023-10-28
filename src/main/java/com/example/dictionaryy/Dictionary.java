package com.example.dictionaryy;
import java.util.ArrayList;

public class Dictionary {
    private ArrayList<Word> List = new ArrayList<>();
    public ArrayList<Word> getList()
    {
        return this.List;
    }

    /**
     * Inserts a word into the dictionary.
     *
     * @param word the word to be inserted
     */

    public void insert(Word word) {
        List.add(word);
    }

}
