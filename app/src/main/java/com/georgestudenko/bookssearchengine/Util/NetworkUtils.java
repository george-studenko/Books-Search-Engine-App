package com.georgestudenko.bookssearchengine.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by george on 29/04/2017.
 */

public class NetworkUtils {

    private final static String secureScheme="https";
    private final static String host = "www.googleapis.com";
    private final static String apiVersion="v1";
    private final static String type="books";
    private final static String concept="volumes";
    private final static String query="q";
    private final static String maxResults="maxResults";
    private final static String maxResultsValue="20";
    private final static String apiKey = BuildConfig.API_KEY.length()>0 ? BuildConfig.API_KEY : "";


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager
                cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    public static URL buildUrl(String searchTerm){
        URL URL= null;
        Uri.Builder builder= new Uri.Builder();
        builder.scheme(secureScheme)
                .authority(host)
                .appendPath(type)
                .appendPath(apiVersion)
                .appendPath(concept)
                .appendQueryParameter(query, searchTerm)
                .appendQueryParameter(maxResults,maxResultsValue);

                if(apiKey!=""){
                    builder.appendQueryParameter("key", apiKey);
                }

                builder.build();
        try {
            URL = new URL(builder.build().toString());
        }catch (MalformedURLException ex){
            System.out.println("ERROR PARSING URL: ");
            ex.printStackTrace();
        }
        return URL;
    }
}
