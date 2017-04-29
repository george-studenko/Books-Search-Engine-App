package com.georgestudenko.bookssearchengine.Data;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import com.georgestudenko.bookssearchengine.Models.Book;
import com.georgestudenko.bookssearchengine.Util.BookParser;
import com.georgestudenko.bookssearchengine.Util.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by george on 29/04/2017.
 */

public class BookAsyncTaskLoader extends AsyncTaskLoader<List<Book>> {
    private String mSearchTerm;
    public BookAsyncTaskLoader(Context context, Bundle bundle) {
        super(context);
        mSearchTerm = bundle.getString("searchTerm");
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        URL url = NetworkUtils.buildUrl(mSearchTerm);
        List<Book> list = null;
        try {
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            if(json!=null && json.length()>0){
                list = BookParser.parseBooks(json);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
