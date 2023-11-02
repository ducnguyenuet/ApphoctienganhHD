package com.example.dictionaryy;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    private String url;
    private Connection cont;
    private Statement stm;
    private ResultSet rs;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Connection getCont() {
        return cont;
    }

    public void setCont(Connection cont) {
        this.cont = cont;
    }

    public Statement getStm() {
        return stm;
    }

    public void setStm(Statement stm) {
        this.stm = stm;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public Database() {
        try {
            this.url = "jdbc:sqlite:src\\main\\resources\\data\\Words.sqlite";
            cont = DriverManager.getConnection(url);
            stm = cont.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void close() {
        try {
            cont.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private String getTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date);
        return time;
    }

    public void addNewWord(WordOfDB word)
    {
        try{
            PreparedStatement ppsm = cont.prepareStatement("INSERT INTO bookmark (time, source, target, audio, pronounce, type, definition, example, synonyms, targetLang) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ppsm.setString(1,getTime());
            ppsm.setString(2,word.getWord_target());
            ppsm.setString(3, word.getWord_explain());
            ppsm.setString(4, word.getAudio());
            ppsm.setString(5, word.getPronounce());
            ppsm.setString(6, word.getType());
            ppsm.setString(7, word.getDefinition());
            ppsm.setString(8, word.getExample());
            ppsm.setString(9, word.getSynonyms());
            ppsm.setString(10, word.getTargetLang());
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteThisWord(String WordTarget)
    {
        try{
            PreparedStatement ppsm = cont.prepareStatement("DELETE FROM bookmark WHERE source = ?");
            ppsm.setString(1,WordTarget);
            ppsm.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ChangeThisWord(String WordTarget,WordOfDB word)
    {
        try{
            PreparedStatement ppsm = cont.prepareStatement("UPDATE bookmark SET time = ?, source = ?, target = ?, pronounce = ?, type = ?, definition = ?, example = ?, synonyms = ?, targetLang = ? WHERE source = ?");
            ppsm.setString(1,getTime());
            ppsm.setString(2,word.getWord_target());
            ppsm.setString(3, word.getWord_explain());
            ppsm.setString(4, word.getAudio());
            ppsm.setString(5, word.getPronounce());
            ppsm.setString(6, word.getType());
            ppsm.setString(7, word.getDefinition());
            ppsm.setString(8, word.getExample());
            ppsm.setString(9, word.getSynonyms());
            ppsm.setString(10, word.getTargetLang());
            ppsm.setString(11, WordTarget);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<WordOfDB> getAllWord()
    {
        ArrayList<WordOfDB> List = new ArrayList<>();
        try{
            rs = stm.executeQuery("SELECT DISTINCT * FROM bookmark");
            while(rs.next())
            {
                String time = rs.getString("time");
                String source = rs.getString("source");
                String target = rs.getString("target");
                String audio = rs.getString("audio");
                String pronounce = rs.getString("pronounce");
                String type = rs.getString("type");
                String definition = rs.getString("definition");
                String example = rs.getString("example");
                String synonyms = rs.getString("synonyms");
                String targetLang = rs.getString("targetLang");
                WordOfDB word = new WordOfDB(time, source, target, audio, pronounce, type, definition, example, synonyms, targetLang);
                List.add(word);
            }
            List.sort((o1, o2) -> o1.getWord_target().compareTo(o2.getWord_target()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return List;
    }
}
