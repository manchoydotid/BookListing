package com.example.manchoy.booklisting;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getName();

    public static ArrayList<Book> extractBookData(String requestUrl){
        URL url = createURL(requestUrl);

        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG,"Problem making the HTTP request.", e);
        }

        ArrayList<Book> bookList = extractItemFromJson(jsonResponse);
        return bookList;
    }

    private QueryUtils(){
    }

    private static URL createURL(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";

        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e(LOG_TAG, "Error response code : " +urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the book JSON result.", e);
        }finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }if(inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<Book> extractItemFromJson(String booksJSON){
        if(TextUtils.isEmpty(booksJSON)){
            return  null;
        }

        ArrayList<Book> books = new ArrayList<>();

        try{
            JSONObject root = new JSONObject(booksJSON);
            JSONArray itemsArray = root.getJSONArray("items");

            for (int i = 0; i < itemsArray.length(); i++){
                JSONObject firstBook = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = firstBook.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");

                JSONArray authorArr = volumeInfo.getJSONArray("authors");
                String author = authorArr.getString(0);

                String desc = volumeInfo.getString("description");

//                TODO Kalo search "Bumi Manusia" ada result yang imageLinksnya ga ada
                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                String thumbnail = imageLinks.getString("thumbnail");

                Book book = new Book(title, author, desc, thumbnail);
                books.add(book);
            }
        }catch (JSONException e){
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return books;
    }
}
