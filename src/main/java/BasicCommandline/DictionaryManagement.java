package BasicCommandline;
import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
public class DictionaryManagement {
    public static void insertFromCommandline(Dictionary dict){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of words to be included in the dictionary: ");
        int numOfWords = input.nextInt();
        input.nextLine();
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

    public static void insertFromFile(Dictionary dict) {
        String path = new File("").getAbsolutePath() + "\\src\\main\\java\\com\\example\\dictionaryy\\dictionaries.txt";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\t"); // Tách từ và giải nghĩa bằng dấu tab
                if (parts.length == 2) {
                    String targetWord = parts[0];
                    String explainWord = parts[1];
                    Word newWord = new Word(targetWord, explainWord);
                    dict.insert(newWord);
                }
            }
        } catch (FileNotFoundException fnfe) {
        System.out.println("The specified file not found" + fnfe);
    } catch (IOException ioe) {
        System.out.println("I/O Exception: " + ioe);
    }
    }

    public static void dictionaryExportToFile(Dictionary dict) {
        String path = new File("").getAbsolutePath() + "\\src\\main\\java\\com\\example\\dictionaryy\\exportedDictionary.txt";

        try {
            File file = new File(path);

            // Kiểm tra xem tệp tin đã tồn tại chưa, nếu có rồi thì xóa và tạo tệp tin mới
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                ArrayList<Word> wordList = dict.getList();

                for (Word word : wordList) {
                    String targetWord = word.getWord_target();
                    String explainWord = word.getWord_explain();

                    
                    bufferedWriter.write(targetWord + "\t" + explainWord);
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();

        } catch (IOException ioe) {
            System.out.println("I/O Exception: " + ioe);
        }
    }

}
