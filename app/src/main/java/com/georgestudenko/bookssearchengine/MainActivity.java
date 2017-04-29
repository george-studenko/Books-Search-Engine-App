package com.georgestudenko.bookssearchengine;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.georgestudenko.bookssearchengine.Data.BookAdapter;
import com.georgestudenko.bookssearchengine.Data.BookAsyncTaskLoader;
import com.georgestudenko.bookssearchengine.Models.Book;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>{

    private static final int LOADER_ID = 182;
    EditText mSearchTerm;
    Button mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchTerm = (EditText) findViewById(R.id.searchText);
        mMessageText = (TextView) findViewById(R.id.messageText);
        mSearch = (Button) findViewById(R.id.searchButton);
    public void searchBooks(View v){
            Bundle bundle = new Bundle();
            bundle.putString("searchTerm", mSearchTerm.getText().toString());
            getSupportLoaderManager().restartLoader(LOADER_ID,bundle,this);
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookAsyncTaskLoader(this,args);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        mAdapter = new BookAdapter(this,data);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
            mAdapter = null;
             mListView.setAdapter(mAdapter);
    }
}
