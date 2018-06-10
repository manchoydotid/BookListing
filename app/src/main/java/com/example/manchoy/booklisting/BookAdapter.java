package com.example.manchoy.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> books){
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView titleView = (TextView)listItemView.findViewById(R.id.title);
        String title = new String(currentBook.getmTitle());
        titleView.setText(title);

        TextView authorView = (TextView)listItemView.findViewById(R.id.author);
        String author = new String(currentBook.getmAuthor());
        authorView.setText(author);

        TextView descView = (TextView)listItemView.findViewById(R.id.description);
        String desc = new String(currentBook.getmDesc());
        descView.setText(desc);

        ImageView thumbView = (ImageView)listItemView.findViewById(R.id.thumbnail);
        Glide.with(getContext())
            .load(currentBook.getmThumb())
                .into(thumbView);

        return listItemView;
    }
}
