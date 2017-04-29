package com.georgestudenko.bookssearchengine.Util;

import com.georgestudenko.bookssearchengine.Models.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.georgestudenko.bookssearchengine.R.id.authors;

/**
 * Created by george on 29/04/2017.
 */

public class BookParser {
    public static List<Book> parseBooks(String json){
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
                StringBuilder authorBuilder =  new StringBuilder();
                JSONArray authors = volumeInfo.getJSONArray("authors");

                for(int n = 0 ; n < authors.length(); n++){
                    authorBuilder.append(authors.get(n));
                    if(n+1 != authors.length()){
                        authorBuilder.append(", ");
                    }
                }
                String author = authorBuilder.toString();
                String pages = (volumeInfo.has("pageCount") ? volumeInfo.getString("pageCount") : "");
                String thumbnail = (imageLinks.has("thumbnail")) ? imageLinks.getString("thumbnail") : (imageLinks.has("smallThumbnail") ? imageLinks.getString("smallThumbnail") : "");
                String shortDescription = (volumeInfo.has("description") ? volumeInfo.getString("description").length()>150 ? volumeInfo.getString("description").substring(0,150)+"..." : volumeInfo.getString("description") : "Description not available");

                String buyLink = null;
                String currency = null;
                String retailPrice = null;
                String saleable= saleInfo.getString("saleability");
                if(saleable.equals("FOR_SALE")){
                    JSONObject reatailPriceJson = saleInfo.getJSONObject("retailPrice");
                    currency = reatailPriceJson.getString("currencyCode").equals("EUR") ? "€" : reatailPriceJson.getString("currencyCode") ;
                    retailPrice = reatailPriceJson.getString("amount");
                    buyLink = saleInfo.getString("buyLink");
                }else{

                }

                Book book = new Book(title,subTitle,author,shortDescription,thumbnail,pages,buyLink,"#","#",currency,retailPrice);
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
