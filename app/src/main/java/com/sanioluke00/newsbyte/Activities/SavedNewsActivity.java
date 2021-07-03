package com.sanioluke00.newsbyte.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sanioluke00.newsbyte.Adapters.SavedNewsAdapter;
import com.sanioluke00.newsbyte.DB.SaveDBHelper;
import com.sanioluke00.newsbyte.Models.NewsModel;
import com.sanioluke00.newsbyte.R;

import java.util.ArrayList;

public class SavedNewsActivity extends AppCompatActivity {

    Button saved_news_back_btn;
    RecyclerView saved_news_rec;
    SaveDBHelper saveDBHelper;
    TextView saved_news_nodata_txt;
    ArrayList<NewsModel> saved_news_arr = new ArrayList<>();
    SavedNewsAdapter savedNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);

        saved_news_back_btn = findViewById(R.id.saved_news_back_btn);
        saved_news_rec = findViewById(R.id.saved_news_rec);
        saved_news_nodata_txt = findViewById(R.id.saved_news_nodata_txt);
        saveDBHelper = new SaveDBHelper(getApplicationContext());

        saved_news_back_btn.setOnClickListener(v -> finish());

        checkAddSavedNews();

    }

    private void checkAddSavedNews() {

        saved_news_arr = saveDBHelper.getAllSavedNews();
        if (saved_news_arr.size() > 0) {
            saved_news_nodata_txt.setVisibility(View.GONE);
            saved_news_rec.setVisibility(View.VISIBLE);

            savedNewsAdapter = new SavedNewsAdapter(getApplicationContext(), saved_news_arr);
            saved_news_rec.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            savedNewsAdapter.notifyDataSetChanged();
            saved_news_rec.setAdapter(savedNewsAdapter);
        } else {
            saved_news_nodata_txt.setVisibility(View.VISIBLE);
            saved_news_rec.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAddSavedNews();
    }
}