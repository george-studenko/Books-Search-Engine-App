package com.georgestudenko.bookssearchengine.Util;

import android.content.Context;

import com.georgestudenko.bookssearchengine.Models.Book;
import com.georgestudenko.bookssearchengine.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 29/04/2017.
 */

public class BookParser {
    public static List<Book> parseBooks(String json, Context context){
        List<Book> books = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(json);
            JSONArray items = root.getJSONArray("items");
            for (int i = 0; i < items.length(); i++){
                JSONObject volumeInfo = items.getJSONObject(i).getJSONObject("volumeInfo");
                JSONObject saleInfo = items.getJSONObject(i).getJSONObject("saleInfo");
                JSONObject accessInfo = items.getJSONObject(i).getJSONObject("accessInfo");

                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");

                String title = (volumeInfo.has("title") ? volumeInfo.getString("title") : "");
                String subTitle = (volumeInfo.has("subTitle") ? volumeInfo.getString("subtitle") : "");

                String author = "Unknown";
                StringBuilder authorBuilder =  new StringBuilder();

                if(volumeInfo.has("authors")) {
                    JSONArray authors = volumeInfo.getJSONArray("authors");

                    for (int n = 0; n < authors.length(); n++) {
                        authorBuilder.append(authors.get(n));
                        if (n + 1 != authors.length()) {
                            authorBuilder.append(", ");
                        }
                    }
                    author = authorBuilder.toString();
                }

                String pages = (volumeInfo.has("pageCount") ? volumeInfo.getString("pageCount") : "");
                String thumbnail = (imageLinks.has("thumbnail")) ? imageLinks.getString("thumbnail") : (imageLinks.has("smallThumbnail") ? imageLinks.getString("smallThumbnail") : "");
                String shortDescription = (volumeInfo.has("description") ? volumeInfo.getString("description").length()>150 ? volumeInfo.getString("description").substring(0,150)+"..." : volumeInfo.getString("description") : context.getString(R.string.description_not_available));

                String buyLink = null;
                String currency = null;
                String retailPrice = null;
                String saleable= saleInfo.getString("saleability");
                if(saleable.equals("FOR_SALE")){
                    JSONObject reatailPriceJson = saleInfo.getJSONObject("retailPrice");
                    currency = reatailPriceJson.getString("currencyCode").equals("EUR") ? "€" : reatailPriceJson.getString("currencyCode") ;
                    retailPrice = reatailPriceJson.getString("amount");
                    buyLink = saleInfo.getString("buyLink");
                }

                String previewLink = null;
                boolean previewAvailable = false;

                if(accessInfo.has("epub")) {
                    JSONObject epub = accessInfo.getJSONObject("epub");
                    if (epub.has("acsTokenLink")) {
                        previewAvailable = true;
                        previewLink = epub.getString("acsTokenLink");
                    }
                }

                if(!previewAvailable){
                    if(accessInfo.has("pdf")) {
                        JSONObject pdf = accessInfo.getJSONObject("pdf");
                        if (pdf.has("acsTokenLink")) {
                            previewAvailable = true;
                            previewLink = pdf.getString("acsTokenLink");
                        }
                    }
                }

                if(!previewAvailable){
                    if(accessInfo.has("webReaderLink")) {
                            previewAvailable = true;
                            previewLink = accessInfo.getString("webReaderLink");
                    }
                }

                if(!previewAvailable){
                    if(volumeInfo.has("previewLink")) {
                            previewAvailable = true;
                            previewLink = volumeInfo.getString("previewLink");
                    }
                }

                String infoLink = null;
                    if(volumeInfo.has("infoLink")) {
                        infoLink = volumeInfo.getString("infoLink");
                    }


                Book book = new Book(title,subTitle,author,shortDescription,thumbnail,pages,buyLink,previewLink,infoLink,currency,retailPrice);
                if(book !=null) {
                    books.add(book);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  books;
    }
}
