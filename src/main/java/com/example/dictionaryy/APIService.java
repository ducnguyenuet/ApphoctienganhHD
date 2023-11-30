package com.example.dictionaryy;

import java.io.IOException;

public interface APIService {
    String translate(String langFrom, String langTo, String text) throws IOException;
    void playTextToSpeech(String lang, String text) throws IOException;
}
