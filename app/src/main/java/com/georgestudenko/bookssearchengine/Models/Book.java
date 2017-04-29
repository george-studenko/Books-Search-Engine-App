package com.georgestudenko.bookssearchengine.Models;

/**
 * Created by george on 29/04/2017.
 */

public class Book {
    private String mTitle;
    private String mSubTitle;
    private String mAuthor;
    private String mShortDesc;
    private String mThumbnail;
    private String mPages;
    private String mBuyLink;
    private String mPreviewLink;
    private String mInfoLink;
    private String mCurrency;
    private String mRetailPrice;

    public Book(String title, String subTitle, String author, String shortDesc, String thumbnail, String pages, String buyLink, String previewLink, String infoLink, String currency, String retailPrice) {
        mTitle = title;
        mSubTitle = subTitle;
        mAuthor = author;
        mShortDesc = shortDesc;
        mThumbnail = thumbnail;
        mPages = pages;
        mBuyLink = buyLink;
        mPreviewLink = previewLink;
        mInfoLink = infoLink;
        mCurrency = currency;
        mRetailPrice = retailPrice;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSubTitle() {
        return mSubTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getShortDesc() {
        return mShortDesc;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public String getPages() {
        return mPages;
    }

    public String getBuyLink() {
        return mBuyLink;
    }

    public String getPreviewLink() {
        return mPreviewLink;
    }

    public String getInfoLink() {
        return mInfoLink;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public String getRetailPrice() {
        return mRetailPrice;
    }
}
