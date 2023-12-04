package com.example.dictionaryy;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

public class ImageTranslator {

    public static String uploadImageToImgur(String imagePath) {
        String apiKey = "e8afee026ba7e40";
        String uploadUrl = "https://api.imgur.com/3/image";

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(new URI(uploadUrl))
                    .header("Authorization", "Client-ID " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofFile(Paths.get(imagePath)));

            HttpRequest request = builder.build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                JSONObject data = jsonResponse.getJSONObject("data");
                return data.getString("link");
            } else {
                System.out.println("Failed to upload the image. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String translateImage(String imageUrl, String lang) {
        String res = "";
        String apiKey = "def4b7b94amsh8a0eaad81ccfcd4p1ed3b8jsnac6b027561a9";
        String apiUrl = "https://easy-transalation-api.p.rapidapi.com/api/v1/translator/translateFromImage";

        HttpClient client = HttpClient.newHttpClient();
        String requestBody = "{\r\n    \"image\": \"" + imageUrl + "\",\r\n    \"to\": \"" + lang + "\"\r\n}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "easy-transalation-api.p.rapidapi.com")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                res = printTranslatedText(response.body());
            } else {
                res = "Failed to get translation. Status code: " + response.statusCode();
            }
        } catch (Exception e) {
            res = "Error occurred: " + e.getMessage();
        }
        return res;
    }

    public static String printTranslatedText(String responseBody) {
        String res = "";
        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONObject dataObject = jsonResponse.getJSONObject("data");
        JSONArray translateTextArray = dataObject.getJSONArray("translateText");

        // Lặp qua mảng để lấy nội dung trong trường "to"
        for (int i = 0; i < translateTextArray.length(); i++) {
            JSONObject translationObject = translateTextArray.getJSONObject(i);
            String translatedText = translationObject.getString("to").toUpperCase();
            res = res + translatedText;
            System.out.println(translatedText);
        }
        int count = res.split("\n").length - 1; // Trừ 1 vì split tạo ra một phần tử trắng ở đầu chuỗi
        System.out.println("Số lượng ký tự xuống dòng trong chuỗi là: " + count);
        System.out.println(res);
        return res;
    }
}
