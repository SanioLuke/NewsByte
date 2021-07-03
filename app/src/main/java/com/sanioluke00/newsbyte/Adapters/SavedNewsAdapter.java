package com.sanioluke00.newsbyte.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sanioluke00.newsbyte.Activities.NewsDisplayActivity;
import com.sanioluke00.newsbyte.Models.NewsModel;
import com.sanioluke00.newsbyte.R;

import java.util.ArrayList;

public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.ListViewHolder> {

    Context context;
    ArrayList<NewsModel> saved_news_array;

    public SavedNewsAdapter(Context context, ArrayList<NewsModel> saved_news_array) {
        this.context = context;
        this.saved_news_array = saved_news_array;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_news_items, null);
        return new ListViewHolder(inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

        NewsModel savednewsModel = saved_news_array.get(position);

        Glide.with(context).load(savednewsModel.getUrlToImage()).into(holder.saved_news_image);
        holder.saved_news_source_name.setText("#" + savednewsModel.getSource_name());
        holder.saved_news_headline_title.setText(savednewsModel.getTitle());
        holder.saved_news_date.setText(savednewsModel.getPublishedAt().substring(0, 10));
        holder.saved_news_author.setText(savednewsModel.getAuthor());

        holder.saved_news_mainlay.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewsDisplayActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("newsmodeldata", savednewsModel);
            context.startActivity(intent);
        });
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
        return saved_news_array.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        ImageView saved_news_image;
        TextView saved_news_source_name, saved_news_headline_title, saved_news_date, saved_news_author;
        View saved_news_mainlay;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            saved_news_image = itemView.findViewById(R.id.saved_news_image);
            saved_news_source_name = itemView.findViewById(R.id.saved_news_source_name);
            saved_news_headline_title = itemView.findViewById(R.id.saved_news_headline_title);
            saved_news_date = itemView.findViewById(R.id.saved_news_date);
            saved_news_author = itemView.findViewById(R.id.saved_news_author);
            saved_news_mainlay = itemView.findViewById(R.id.saved_news_mainlay);
        }
    }
}
