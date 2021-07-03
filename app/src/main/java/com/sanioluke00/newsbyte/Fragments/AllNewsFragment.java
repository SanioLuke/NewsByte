package com.sanioluke00.newsbyte.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.sanioluke00.newsbyte.Activities.WebPageActivity;
import com.sanioluke00.newsbyte.DB.SaveDBHelper;
import com.sanioluke00.newsbyte.Models.NewsModel;
import com.sanioluke00.newsbyte.R;

import org.jetbrains.annotations.NotNull;

public class AllNewsFragment extends Fragment {

    ImageView allnewsfrag_image;
    TextView allnewsfrag_header, allnewsfrag_des, allnewsfrag_content, allnewsfrag_author, allnewsfrag_timesource;
    View allnews_linklay;
    ImageButton allnewsfrag_save_btn;
    NewsModel each_news;
    SaveDBHelper saveDBHelper;

    public AllNewsFragment() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_news, container, false);

        allnewsfrag_image = view.findViewById(R.id.allnewsfrag_image);
        allnewsfrag_header = view.findViewById(R.id.allnewsfrag_header);
        allnewsfrag_des = view.findViewById(R.id.allnewsfrag_des);
        allnewsfrag_content = view.findViewById(R.id.allnewsfrag_content);
        allnewsfrag_author = view.findViewById(R.id.allnewsfrag_author);
        allnewsfrag_timesource = view.findViewById(R.id.allnewsfrag_timesource);
        allnews_linklay = view.findViewById(R.id.allnews_linklay);
        allnewsfrag_save_btn = view.findViewById(R.id.allnewsfrag_save_btn);
        saveDBHelper = new SaveDBHelper(getContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
            each_news = (NewsModel) bundle.getSerializable("all_news");
            if (each_news != null) {

                newsSavedFun();

                Glide.with(view.getContext()).load(each_news.getUrlToImage()).into(allnewsfrag_image);
                if (each_news.getTitle() != null)
                    allnewsfrag_header.setText("" + each_news.getTitle());
                else
                    allnewsfrag_header.setText("News Headline");

                if (each_news.getDescription() != null)
                    allnewsfrag_des.setText("" + each_news.getDescription());
                else
                    allnewsfrag_des.setText("");

                if (each_news.getContent() != null) {
                    String content = each_news.getContent();
                    int startindex = content.indexOf("[+");
                    if (startindex > -1) {
                        String removestring = content.substring(startindex);
                        content = content.replace(removestring, "");
                    }
                    allnewsfrag_content.setText("" + content);
                } else
                    allnewsfrag_content.setText("To read more, click below");

                if (each_news.getAuthor() != null && !each_news.getAuthor().contains("null"))
                    allnewsfrag_author.setText("News from - " + each_news.getAuthor());
                else
                    allnewsfrag_author.setText("");

                if (each_news.getSource_name() != null || each_news.getPublishedAt() != null) {
                    if (each_news.getSource_name() != null && each_news.getPublishedAt() == null)
                        allnewsfrag_timesource.setText("#" + each_news.getSource_name() + " | Published today");
                    else if (each_news.getSource_name() == null && each_news.getPublishedAt() != null)
                        allnewsfrag_timesource.setText("#HotNews | Published at : " + each_news.getPublishedAt().substring(0, 10));
                    else if (each_news.getSource_name() != null && each_news.getPublishedAt() != null) {
                        allnewsfrag_timesource.setText("#" + each_news.getSource_name() + " | Published at : " + each_news.getPublishedAt().substring(0, 10));
                    }
                } else {
                    allnewsfrag_timesource.setText("#HotNews | Published today");
                }

                allnews_linklay.setOnClickListener(v -> {
                    Intent intent = new Intent(getContext(), WebPageActivity.class);
                    intent.putExtra("newstitle", each_news.getTitle());
                    intent.putExtra("newsURL", each_news.getUrl());
                    startActivity(intent);
                });

                allnewsfrag_save_btn.setOnClickListener(v -> {
                    if (saveDBHelper.checkNews(each_news.getUrl())) {
                        saveDBHelper.deleteNews(each_news.getUrl());
                    } else {
                        saveDBHelper.add_SaveNews(each_news.getAuthor(), each_news.getTitle(), each_news.getDescription(), each_news.getUrl(),
                                each_news.getUrlToImage(), each_news.getPublishedAt(), each_news.getContent(), each_news.getSource_name());
                    }
                    newsSavedFun();
                });
            }
        }

        return view;
    }

    private void newsSavedFun() {
        if (saveDBHelper.checkNews(each_news.getUrl())) {
            allnewsfrag_save_btn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.main_color)));
            allnewsfrag_save_btn.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_bookmark_added));
        } else {
            allnewsfrag_save_btn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.darkish_medium_grey)));
            allnewsfrag_save_btn.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_bookmark));
        }
    }
}