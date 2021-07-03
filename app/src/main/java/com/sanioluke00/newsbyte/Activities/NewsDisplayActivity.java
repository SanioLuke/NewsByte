package com.sanioluke00.newsbyte.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.sanioluke00.newsbyte.DB.SaveDBHelper;
import com.sanioluke00.newsbyte.Models.NewsModel;
import com.sanioluke00.newsbyte.R;

public class NewsDisplayActivity extends AppCompatActivity {

    NewsModel all_news_data;
    ImageButton news_display_back_btn, news_display_save_btn;
    ImageView news_display_image;
    TextView news_display_title, news_display_date, news_display_des, news_display_content;
    Button news_display_readmore_btn;
    SaveDBHelper saveDBHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_display);

        Intent intent = getIntent();
        all_news_data = (NewsModel) intent.getSerializableExtra("newsmodeldata");

        news_display_back_btn = findViewById(R.id.news_display_back_btn);
        news_display_save_btn = findViewById(R.id.news_display_save_btn);
        news_display_title = findViewById(R.id.news_display_title);
        news_display_date = findViewById(R.id.news_display_date);
        news_display_des = findViewById(R.id.news_display_des);
        news_display_content = findViewById(R.id.news_display_content);
        news_display_readmore_btn = findViewById(R.id.news_display_readmore_btn);
        news_display_image = findViewById(R.id.news_display_image);
        saveDBHelper = new SaveDBHelper(getApplicationContext());

        newsSavedFun();

        if (all_news_data != null) {
            Glide.with(getApplicationContext()).load(all_news_data.getUrlToImage()).into(news_display_image);

            if (all_news_data.getTitle() != null && !all_news_data.getTitle().equals("null"))
                news_display_title.setText("" + all_news_data.getTitle());
            else
                news_display_title.setText("Top News Headline");

            if (all_news_data.getDescription() != null && !all_news_data.getDescription().equals("null"))
                news_display_des.setText("" + all_news_data.getDescription());
            else
                news_display_des.setText("");

            if (all_news_data.getContent() != null && !all_news_data.getContent().equals("null")) {
                String content = all_news_data.getContent();
                int startindex = content.indexOf("[+");
                if (startindex > -1) {
                    String removestring = content.substring(startindex);
                    content = content.replace(removestring, "");
                }
                news_display_content.setText("" + content);
            } else
                news_display_content.setText("To read more, click below");

            if (all_news_data.getPublishedAt() != null)
                news_display_date.setText("Published At : " + all_news_data.getPublishedAt().substring(0, 10));
            else
                news_display_date.setText("Published today");
        }


        news_display_back_btn.setOnClickListener(v -> finish());

        news_display_save_btn.setOnClickListener(v -> {
            if (saveDBHelper.checkNews(all_news_data.getUrl())) {
                saveDBHelper.deleteNews(all_news_data.getUrl());
            } else {
                saveDBHelper.add_SaveNews(all_news_data.getAuthor(), all_news_data.getTitle(), all_news_data.getDescription(), all_news_data.getUrl(),
                        all_news_data.getUrlToImage(), all_news_data.getPublishedAt(), all_news_data.getContent(), all_news_data.getSource_name());
            }
            newsSavedFun();
        });

        news_display_readmore_btn.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), WebPageActivity.class);
            intent1.putExtra("newstitle", all_news_data.getTitle());
            intent1.putExtra("newsURL", all_news_data.getUrl());
            startActivity(intent1);
        });
    }

    private void newsSavedFun() {
        if (saveDBHelper.checkNews(all_news_data.getUrl())) {
            news_display_save_btn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.main_color)));
            news_display_save_btn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_bookmark_added));
        } else {
            news_display_save_btn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.darkish_medium_grey)));
            news_display_save_btn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_bookmark));
        }
    }
}