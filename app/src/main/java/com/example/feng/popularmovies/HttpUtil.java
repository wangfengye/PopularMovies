package com.example.feng.popularmovies;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by fengye on 2017/9/20.
 * email 1040441325@qq.com
 */

public  class HttpUtil {
    public static final String KEY_API_KEY = "api_key";
    public static final String KEY_LANGUAGE = "language";
    public static final String ZH = "zh";
    private static final String API_KEY = "63190423708f521c776f6807ff3aad7d";
    public static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    public static String getResponse(String baseUrl){
        Uri uri = Uri.parse(BASE_URL+baseUrl).buildUpon()
                .appendQueryParameter(KEY_API_KEY, API_KEY)
                .appendQueryParameter(KEY_LANGUAGE, ZH)
                .build();
        HttpURLConnection conn = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL url = new URL(uri.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            if (conn.getResponseCode() == 200) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    result += inputLine;
                }
                in.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }
}
