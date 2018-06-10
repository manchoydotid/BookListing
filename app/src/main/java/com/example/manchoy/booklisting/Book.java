package com.example.manchoy.booklisting;

import android.widget.TextView;

public class Book {

    private String mTitle, mAuthor, mDesc, mThumb;

    public Book(String title, String author, String desc, String urlTumb){
        mTitle = title;
        mAuthor = author;
        mDesc = desc;
        mThumb = urlTumb;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public String getmThumb() {
        return mThumb;
    }

    public void setmThumb(String mThumb) {
        this.mThumb = mThumb;
    }
}
