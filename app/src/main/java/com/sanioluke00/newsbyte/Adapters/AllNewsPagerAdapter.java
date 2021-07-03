package com.sanioluke00.newsbyte.Adapters;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.sanioluke00.newsbyte.Fragments.AllNewsFragment;
import com.sanioluke00.newsbyte.Models.NewsModel;

import java.util.ArrayList;

public class AllNewsPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<NewsModel> all_news_array;

    public AllNewsPagerAdapter(@NonNull FragmentManager fm, ArrayList<NewsModel> all_news_array) {
        super(fm);
        this.all_news_array = all_news_array;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        AllNewsFragment allNewsFragment = new AllNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("all_news", all_news_array.get(position));
        allNewsFragment.setArguments(bundle);
        return allNewsFragment;
    }

    @Override
    public int getCount() {
        Log.e("frag_adapter_size", "Size of all news is : " + all_news_array.size());
        return all_news_array.size();
    }
}
