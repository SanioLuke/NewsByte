package com.sanioluke00.newsbyte.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sanioluke00.newsbyte.Activities.NewsDisplayActivity;
import com.sanioluke00.newsbyte.DB.SaveDBHelper;
import com.sanioluke00.newsbyte.Models.NewsModel;
import com.sanioluke00.newsbyte.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TopHeadlineAdapter extends RecyclerView.Adapter<TopHeadlineAdapter.ListViewHolder> {

    Context context;
    ArrayList<NewsModel> topheadline_array;

    public TopHeadlineAdapter(Context context, ArrayList<NewsModel> topheadline_array) {
        this.context = context;
        this.topheadline_array = topheadline_array;
    }

    @NonNull
    @Override
    public TopHeadlineAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.topheadline_items, null);
        return new ListViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull TopHeadlineAdapter.ListViewHolder holder, int position) {

        NewsModel newsModel = topheadline_array.get(position);
        SaveDBHelper saveDBHelper = new SaveDBHelper(context);
        setSaveBtnColor(saveDBHelper, newsModel.getUrl(), holder);

        Glide.with(context).load(newsModel.getUrlToImage()).into(holder.topheadline_image);
        holder.topheadline_title.setText(newsModel.getTitle());
        holder.topheadline_des.setText(newsModel.getDescription());
        holder.topheadline_date.setText(newsModel.getPublishedAt().substring(0, 10));

        holder.topheadline_read_btn.setOnClickListener(v -> loadNewsDetailsPage(newsModel));
        holder.top_headline_mainlay.setOnClickListener(v -> loadNewsDetailsPage(newsModel));

        holder.topheadline_save_btn.setOnClickListener(v -> {
            if (saveDBHelper.checkNews(newsModel.getUrl())) {
                saveDBHelper.deleteNews(newsModel.getUrl());
            } else {
                saveDBHelper.add_SaveNews(newsModel.getAuthor(), newsModel.getTitle(), newsModel.getDescription(), newsModel.getUrl(),
                        newsModel.getUrlToImage(), newsModel.getPublishedAt(), newsModel.getContent(), newsModel.getSource_name());
            }
            setSaveBtnColor(saveDBHelper, newsModel.getUrl(), holder);
        });
    }

    @SuppressLint({"UseCompatTextViewDrawableApis", "SetTextI18n"})
    public void setSaveBtnColor(@NotNull SaveDBHelper saveDBHelper, String url, ListViewHolder holder) {
        if (saveDBHelper.checkNews(url)) {
            holder.topheadline_save_btn.setText("Saved");
            holder.topheadline_save_btn.setCompoundDrawablePadding(10);
            holder.topheadline_save_btn.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_check_circle), null, null, null);
            holder.topheadline_save_btn.setCompoundDrawableTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white)));
            holder.topheadline_save_btn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.main_color)));
        } else {
            holder.topheadline_save_btn.setText("Save");
            holder.topheadline_save_btn.setCompoundDrawablePadding(0);
            holder.topheadline_save_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            holder.topheadline_save_btn.setCompoundDrawableTintList(null);
            holder.topheadline_save_btn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.dark_grey)));
        }
    }

    public void loadNewsDetailsPage(NewsModel newsModel) {
        Intent intent = new Intent(context, NewsDisplayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("newsmodeldata", newsModel);
        context.startActivity(intent);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return topheadline_array.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        ImageView topheadline_image;
        TextView topheadline_title, topheadline_des, topheadline_read_btn, topheadline_save_btn, topheadline_date;
        View top_headline_mainlay;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            topheadline_image = itemView.findViewById(R.id.topheadline_image);
            topheadline_title = itemView.findViewById(R.id.topheadline_title);
            topheadline_des = itemView.findViewById(R.id.topheadline_des);
            topheadline_read_btn = itemView.findViewById(R.id.topheadline_read_btn);
            topheadline_save_btn = itemView.findViewById(R.id.topheadline_save_btn);
            topheadline_date = itemView.findViewById(R.id.topheadline_date);
            top_headline_mainlay = itemView.findViewById(R.id.top_headline_mainlay);
        }
    }
}
