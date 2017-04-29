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
    private final static String maxResultsValue="40";

    public static URL buildUrl(String searchTerm){
        URL URL= null;
        Uri.Builder builder= new Uri.Builder();
        builder.scheme(secureScheme)
                .authority(host)
                .appendPath(type)
                .appendPath(apiVersion)
                .appendPath(concept)
                .appendQueryParameter(query, searchTerm)
                .appendQueryParameter(maxResults,maxResultsValue)
                .build();
        try {
            URL = new URL(builder.build().toString());
        }catch (MalformedURLException ex){
            System.out.println("ERROR PARSING URL: ");
            ex.printStackTrace();
        }
        return URL;
    }
}
