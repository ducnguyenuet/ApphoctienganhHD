package com.example.dictionaryy;

import BasicCommandline.Word;

public class WordOfDB extends Word {
    private String time;
    private String audio;
    private String pronounce;
    private String synonyms;
    private String targetLang;
    private String example;
    private String info;

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    private String definition;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

    public String getTargetLang() {
        return targetLang;
    }

    public void setTargetLang(String targetLang) {
        this.targetLang = targetLang;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public WordOfDB()
    {
        word_target = " ";
        word_explain= " ";
    }

    public WordOfDB(String Time,String wordTarget,String wordExplain,String Audio,String Pronounce,String Type,String Definition,String Example, String Synonyms,String TargetLang)
    {
        time = Time;
        word_target = wordTarget.trim().toLowerCase();
        word_explain = wordExplain.trim().toLowerCase();
        audio = Audio;
        targetLang = TargetLang;
        pronounce = Pronounce;
        synonyms = Synonyms;
        type = Type;
        example = Example;
        definition = Definition;
    }

    @Override
    public String toString()
    {
        return word_target;
    }

}
