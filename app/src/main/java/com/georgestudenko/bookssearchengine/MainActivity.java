package com.georgestudenko.bookssearchengine;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.georgestudenko.bookssearchengine.Data.BookAdapter;
import com.georgestudenko.bookssearchengine.Data.BookAsyncTaskLoader;
import com.georgestudenko.bookssearchengine.Models.Book;
import com.georgestudenko.bookssearchengine.Util.NetworkUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>{

    private static final int LOADER_ID = 182;
    private static String errorMessage;
    private static boolean mShowError;
    private static TextView mMessageText;
    private static Button mSearch;
    private String mLastSearch;
    private ProgressBar mProgressBar;
    private EditText mSearchTerm;
    private BookAdapter mAdapter;
    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowError = false;
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageText = (TextView) findViewById(R.id.messageText);
        mSearch = (Button) findViewById(R.id.searchButton);

        checkInternetConnection();

    private void checkInternetConnection() {
        if(!NetworkUtils.isConnected(this)){
            clearAdapter();
            setErrorMessage(getString(R.string.no_internet),false);
            mSearch.setEnabled(false);
        }else{
            mSearch.setEnabled(true);
            mMessageText.setText(R.string.make_a_seach);
        }
    }

        mSearchTerm = (EditText) findViewById(R.id.searchText);

        mListView = (ListView) findViewById(R.id.booksListView);
        mListView.setEmptyView(mMessageText);
    private void clearAdapter() {
        if(mAdapter!=null) {
            mAdapter.clear();
            mAdapter.setNotifyOnChange(true);
        }
    }

    public void searchBooks(View v){
            Bundle bundle = new Bundle();
            bundle.putString("searchTerm", mSearchTerm.getText().toString());
            getSupportLoaderManager().restartLoader(LOADER_ID,bundle,this);
    public static void setErrorMessage(String errorMessageToSet, boolean showErrorOnLoadFinish) {
        errorMessage = errorMessageToSet;
        if(showErrorOnLoadFinish){
            mShowError =true;
        }else {
            mMessageText.setText(errorMessage);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkInternetConnection();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkInternetConnection();
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        mMessageText.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        return new BookAsyncTaskLoader(this,args);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        mMessageText.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);

        if(data !=null) {
            mAdapter = new BookAdapter(this, data);
            mListView.setAdapter(mAdapter);
        }
        if(mShowError){
            mMessageText.setText(errorMessage);
            mSearch.setEnabled(false);
            mShowError=false;
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
            mAdapter = null;
             mListView.setAdapter(mAdapter);
    }
}
