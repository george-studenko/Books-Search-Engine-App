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

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentView = convertView;
        if(currentView == null){
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.book_item,parent,false);
        }
        Book currentBook = mData.get(position);
        TextView title = (TextView) currentView.findViewById(R.id.title);
        TextView authors = (TextView) currentView.findViewById(R.id.authors);
        TextView shortDescription = (TextView) currentView.findViewById(R.id.shortDescription);
        TextView pages = (TextView) currentView.findViewById(R.id.pages);
        Button buy = (Button) currentView.findViewById(R.id.buyButton);
        Button moreInfo = (Button) currentView.findViewById(R.id.openDetails);
        Button getSample = (Button) currentView.findViewById(R.id.downloadSample);
        ImageView thumbnail = (ImageView) currentView.findViewById(R.id.bookThumbnail);

        title.setText(currentBook.getTitle() + (currentBook.getSubTitle().length()>0 ? ": "+currentBook.getSubTitle() : ""));
        authors.setText(currentBook.getAuthor());

        if(currentBook.getShortDesc().length() > 0) {
            shortDescription.setText(currentBook.getShortDesc());
        }
        else {
            shortDescription.setVisibility(View.GONE);
        }

        if(currentBook.getPages().length()>0) {
            pages.setText(currentBook.getPages() + " pages");
        }else{
            pages.setVisibility(View.GONE);
        }
        if(currentBook.getThumbnail().length()>0) {
            Picasso.with(getContext()).load(Uri.parse(currentBook.getThumbnail())).into(thumbnail);
        }

        return currentView;
    }
}
