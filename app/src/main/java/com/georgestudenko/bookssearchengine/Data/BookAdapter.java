package com.georgestudenko.bookssearchengine.Data;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.georgestudenko.bookssearchengine.Models.Book;
import com.georgestudenko.bookssearchengine.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by george on 29/04/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    List<Book> mData;

    public BookAdapter(@NonNull Context context, @NonNull List<Book> books) {
        super(context, 0, books);
        mData = books;
    }

}
