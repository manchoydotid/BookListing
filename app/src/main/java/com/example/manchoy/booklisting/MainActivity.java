package com.example.manchoy.booklisting;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    
    String keyword;
    private EditText wordEditText;
    private Button searchButton;
    private BookAdapter mAdapter;
    private static final String API_KEY = "AIzaSyBWZxUFeGeZ6_DJJZVHGmXNm1VX2h0VT10";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordEditText = (EditText)findViewById(R.id.editText);
        searchButton = (Button)findViewById(R.id.search_btn);

        searchButton.setOnClickListener(myListener);

        ListView bookListView = (ListView)findViewById(R.id.list);
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);


    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(view.getId()==R.id.search_btn){
                keyword = wordEditText.getText().toString();
                if(keyword.contains(" ")){
                    keyword = keyword.replace(" ","+");
                }
                Log.e("Kata yang dicari ", keyword);
                String URL =
                        "https://www.googleapis.com/books/v1/volumes?q="+keyword+"&key="+API_KEY;

                BookAsyncTask task = new BookAsyncTask();
                task.execute(URL);
            }
        }
    };

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
