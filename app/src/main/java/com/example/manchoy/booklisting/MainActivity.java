package com.example.manchoy.booklisting;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    String query;
    private SearchView searchView;
    private BookAdapter mAdapter;
    private static final String API_KEY = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = (SearchView)findViewById(R.id.sV);
        searchView.setOnQueryTextListener(this);

        ListView bookListView = (ListView)findViewById(R.id.list);
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);

    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        if(s.contains(" ")){
            query = s.replace(" ","+");
        }
        String URL =
                "https://www.googleapis.com/books/v1/volumes?q="+query+"&key="+API_KEY;

        BookAsyncTask task = new BookAsyncTask();
        task.execute(URL);

        //kalo returnnya true abis search, keyboardnya ga ilang
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }


    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>>{

        @Override
        protected List<Book> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<Book> result = QueryUtils.extractBookData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            mAdapter.clear();
            if(books!= null && !books.isEmpty()){
                mAdapter.addAll(books);
            }
        }
    }
}
