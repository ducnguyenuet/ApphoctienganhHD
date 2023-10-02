package com.example.dictionaryy;

public class Word {
    private String word_target;
    private String word_explain;

    /**
     * Constructor 1.
     *
     * @param word_target word_target.
     * @param word_explain word_explain.
     */
    public Word(String word_target, String word_explain) {
        this.word_target = word_target.trim().toLowerCase();
        this.word_explain = word_explain.trim().toLowerCase();
    }

    /**
     * Constructor 2.
     */
    public Word() {
        this.word_target = "";
        this.word_explain = "";
    }
    /**
     * Get word_target.
     * @return word_target.
     */
    public String getWord_target(){
        return word_target;
    }

    /**
     * Set word_target.
     *
     * @param t word_target.
     */
    public void setWord_target (String t){
        word_target = t;
    }

    /**
     * Get word_explain.
     * @return word_explain.
     */
    public String getWord_explain() {
        return word_explain;
    }

    /**
     * Set word_explain.
     *
     * @param e word_explain.
     */
    public void setWord_explain (String e) {
        word_explain = e;
    }
}
