package com.example.lab3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RESTController {
    //POST and PUT requests
    public static String sendRequestWithBody(String requestUrl, String bodyParameters, String type) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(type);
        httpURLConnection.setRequestProperty("Content-type", "application/json; charset=UTF-8");

        httpURLConnection.setReadTimeout(20000);
        httpURLConnection.setConnectTimeout(20000);
        httpURLConnection.setRequestProperty("Accept", "application/json");

        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);

        OutputStream outputStream = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        bufferedWriter.write(bodyParameters);
        bufferedWriter.close();
        outputStream.close();
        int code = httpURLConnection.getResponseCode();

        if (code == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            return response.toString();
        } else {
            return "Error";
        }
    }

    //GET or DELETE requests
    public static String sendRequest(String requestUrl, String type) throws  IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(type);
        httpURLConnection.setRequestProperty("Content-type", "application/json; charset=UTF-8");
        int code = httpURLConnection.getResponseCode();

        if (code == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            return response.toString();
        } else {
            return "Error";
        }
    }
}
