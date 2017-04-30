package com.georgestudenko.bookssearchengine.Data;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
    Context mContext;

    public BookAdapter(@NonNull Context context, @NonNull List<Book> books) {
        super(context, 0, books);
        mContext = context;
        mData = books;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentView = convertView;
        if(currentView == null){
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.book_item,parent,false);
        }
        final Book currentBook = mData.get(position);
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
            pages.setText(currentBook.getPages() + mContext.getString(R.string.pages));
        }else{
            pages.setVisibility(View.GONE);
        }
        if(currentBook.getThumbnail().length()>0) {
            Picasso.with(getContext()).load(Uri.parse(currentBook.getThumbnail())).into(thumbnail);
        }

        if(currentBook.getRetailPrice()!=null){
            buy.setVisibility(View.VISIBLE);
            buy.setText(mContext.getString(R.string.buy)+currentBook.getRetailPrice()+" "+ currentBook.getCurrency());

            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(currentBook.getBuyLink()));
                    mContext.startActivity(intent);
                }
            });
        }else  {
            buy.setVisibility(View.GONE);
        }

        if(currentBook.getInfoLink()!=null){
            moreInfo.setVisibility(View.VISIBLE);

            moreInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(currentBook.getInfoLink()));
                    mContext.startActivity(intent);
                }
            });
        }else  {
            moreInfo.setVisibility(View.GONE);
        }

        if(currentBook.getRetailPrice()!=null){
            getSample.setVisibility(View.VISIBLE);

            getSample.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String previewLink = currentBook.getPreviewLink();
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(previewLink));
                    mContext.startActivity(intent);
                }
            });
        }else  {
            getSample.setVisibility(View.GONE);
        }

        return currentView;
    }
}
