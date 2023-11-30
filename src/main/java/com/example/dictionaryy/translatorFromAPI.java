package com.example.dictionaryy;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class translatorFromAPI implements APIService {

    private static final String API_KEY = "def4b7b94amsh8a0eaad81ccfcd4p1ed3b8jsnac6b027561a9";
    private static final String API_HOST = "text-to-speech53.p.rapidapi.com";

    @Override
    public String translate(String langFrom, String langTo, String text) throws IOException {
        String urlStr = "https://script.google.com/macros/s/AKfycbyaYRBQm5ccKlurvadWYgXbuyHzgA9dVV_8dte2Ij-omziHWmnEiUof6gAzma_Lpwzj/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    @Override
    public void playTextToSpeech(String lang, String text) throws IOException {
        try {
            URI uri = URI.create("https://text-to-speech53.p.rapidapi.com/");
            String requestBody = String.format("{\"text\": \"%s\", \"lang\": \"%s\", \"format\": \"wav\"}", text, lang);

            // Create a POST request to get the audio URL from the API
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("content-type", "application/json")
                    .header("X-RapidAPI-Key", API_KEY)
                    .header("X-RapidAPI-Host", API_HOST)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Send the request and retrieve the response
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            // Parse the JSON response to get the speech URL
            String speechURL = responseBody.substring(responseBody.indexOf("https://"), responseBody.lastIndexOf(".wav") + 4);

            // Play the speech from the retrieved URL
            playAudioFromURL(speechURL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void playAudioFromURL(String audioURL) {
        try {
            // Get the audio data from the URL
            HttpResponse<byte[]> audioResponse = HttpClient.newHttpClient()
                    .send(HttpRequest.newBuilder().uri(URI.create(audioURL)).build(), HttpResponse.BodyHandlers.ofByteArray());

            byte[] audioData = audioResponse.body();

            if (audioData != null) {
                // Play the audio
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(audioData));
                AudioFormat format = audioInputStream.getFormat();

                SourceDataLine sourceLine = AudioSystem.getSourceDataLine(format);
                sourceLine.open(format);
                sourceLine.start();

                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                int bytesRead;

                while ((bytesRead = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
                    sourceLine.write(buffer, 0, bytesRead);
                }

                sourceLine.drain();
                sourceLine.stop();
                sourceLine.close();
                audioInputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
