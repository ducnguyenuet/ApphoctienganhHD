package com.example.dictionaryy;
import java.util.Scanner;
public class DictionaryManagement {
    public static void insertFromCommandline(Dictionary dict){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of words to be included in the dictionary: ");
        int numOfWords = input.nextInt();
        while (numOfWords > 0) {
            System.out.print("Enter the word to be included in the dictionary: ");
            String word_target = input.nextLine();
            System.out.print("Enter the meaning of the word: ");
            String word_explain = input.nextLine();
            Word newWord = new Word(word_target, word_explain);
            dict.insert(newWord);
            numOfWords--;
        }
    }
}
