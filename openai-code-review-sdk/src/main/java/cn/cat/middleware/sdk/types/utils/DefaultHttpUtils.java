package cn.cat.middleware.sdk.types.utils;

import com.alibaba.fastjson2.JSON;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class DefaultHttpUtils {

    public static String executeGetRequest(String uri, Map<String, String> headers) throws Exception {
        HttpURLConnection connection = getConnection(uri, "GET", headers);
        return getResult(connection);
    }

    public static String executePostRequest(String uri, Map<String, String> headers, Object body) throws Exception {
        HttpURLConnection connection = getConnection(uri, "POST", headers);

        try (OutputStream writer = connection.getOutputStream()) {
            byte[] input = JSON.toJSONString(body).getBytes(StandardCharsets.UTF_8);
            writer.write(input, 0, input.length);
        }

        return getResult(connection);
    }

    private static HttpURLConnection getConnection(String uri, String method, Map<String, String> headers) throws Exception {
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(method);
        headers.forEach(connection::setRequestProperty);
        connection.setDoOutput(true);
        return connection;
    }

    private static String getResult(HttpURLConnection connection) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            connection.disconnect();
            return content.toString();
        }
    }

}
