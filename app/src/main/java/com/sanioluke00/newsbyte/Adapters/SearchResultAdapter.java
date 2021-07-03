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

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ListViewHolder> {

    Context context;
    ArrayList<NewsModel> searchresult_array;

    public SearchResultAdapter(Context context, ArrayList<NewsModel> searchresult_array) {
        this.context = context;
        this.searchresult_array = searchresult_array;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_news_items, null);
        return new ListViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

        NewsModel search_eachdata = searchresult_array.get(position);

        holder.search_news_headline_title.setText(search_eachdata.getTitle());
        holder.search_news_headline_des.setText(search_eachdata.getDescription());
        Glide.with(context).load(search_eachdata.getUrlToImage()).into(holder.search_news_image);

        holder.search_news_mainlay.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewsDisplayActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("newsmodeldata", search_eachdata);
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
        return searchresult_array.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        ImageView search_news_image;
        TextView search_news_headline_title, search_news_headline_des;
        View search_news_mainlay;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            search_news_mainlay = itemView.findViewById(R.id.search_news_mainlay);
            search_news_image = itemView.findViewById(R.id.search_news_image);
            search_news_headline_title = itemView.findViewById(R.id.search_news_headline_title);
            search_news_headline_des = itemView.findViewById(R.id.search_news_headline_des);
        }
    }
}
