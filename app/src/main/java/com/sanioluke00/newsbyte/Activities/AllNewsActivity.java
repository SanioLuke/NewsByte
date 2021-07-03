package com.sanioluke00.newsbyte.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sanioluke00.newsbyte.Adapters.AllNewsPagerAdapter;
import com.sanioluke00.newsbyte.Adapters.AllNewsViewPager;
import com.sanioluke00.newsbyte.Fragments.AllNewsFilterDialogFragment;
import com.sanioluke00.newsbyte.Models.AllData;
import com.sanioluke00.newsbyte.Models.NewsModel;
import com.sanioluke00.newsbyte.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AllNewsActivity extends AppCompatActivity implements AllNewsFilterDialogFragment.onMultiChoiceListner {

    AllData allData = new AllData();
    ImageButton allnews_filter, allnews_back_btn;
    TextView allnews_title;
    ArrayList<String> allsource = allData.getSource();
    ArrayList<String> getselectedsource = new ArrayList<>();
    private AllNewsViewPager allnews_viewpager;
    private AllNewsPagerAdapter allNewsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_news);

        getselectedsource = allsource;
        allnews_viewpager = findViewById(R.id.allnews_viewpager);
        allnews_title = findViewById(R.id.allnews_title);
        allnews_filter = findViewById(R.id.allnews_filter);
        allnews_back_btn = findViewById(R.id.allnews_back_btn);

        allNewsData(allsource);

        allnews_back_btn.setOnClickListener(v -> finish());

        allnews_filter.setOnClickListener(v -> {


            ArrayList<Boolean> stockList = new ArrayList<>();
            for (int m = 0; m < allsource.size(); m++) {
                for (int n = 0; n < getselectedsource.size(); n++) {
                    if (allsource.get(m).equals(getselectedsource.get(n))) {
                        stockList.add(m, true);
                        break;
                    } else {
                        stockList.add(m, null);
                    }
                }
                if (stockList.get(m) == null) {
                    stockList.add(m, false);
                }
            }
            stockList.removeAll(Collections.singleton(null));

            Boolean[] stockArr = new Boolean[stockList.size()];
            boolean[] stocklis = new boolean[stockList.size()];
            stockArr = stockList.toArray(stockArr);
            for (int ch = 0; ch < stockArr.length; ch++) {
                stocklis[ch] = stockArr[ch];
            }

            for (int d = 0; d < stockArr.length; d++) {
                Log.d("ss_array", "Hello : " + stockArr[d] + " Size : " + d);
            }

            DialogFragment prefschoicedialog = new AllNewsFilterDialogFragment(stocklis, getselectedsource);
            prefschoicedialog.setCancelable(true);
            prefschoicedialog.show(getSupportFragmentManager(), "Multiple Choice Dialog");
        });
    }

    private boolean @NotNull [] sourceBooleanArrayToPrimitiveArray(@NotNull ArrayList<Boolean> booleanList) {
        final boolean[] primitivesList = new boolean[booleanList.size()];
        int index = 0;
        for (Boolean each_obj : booleanList) primitivesList[index++] = each_obj;
        return primitivesList;
    }

    private void allNewsData(@NotNull ArrayList<String> allsource) {

        ArrayList<NewsModel> all_news = new ArrayList<>();
        for (int j = 0; j < allsource.size(); j++) {
            RequestQueue queue = Volley.newRequestQueue(this);
            int finalJ = j;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://saurav.tech/NewsAPI/everything/" + allsource.get(j) + ".json",
                    new Response.Listener<String>() {

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject main_jsonObject = new JSONObject(response);
                                for (int i = 0; i < main_jsonObject.getJSONArray("articles").length(); i++) {
                                    String news_array = main_jsonObject.getJSONArray("articles").getString(i);
                                    JSONObject news_obj = new JSONObject(news_array);
                                    JSONObject source = news_obj.getJSONObject("source");

                                    NewsModel newsModel = new NewsModel(
                                            news_obj.getString("author"),
                                            news_obj.getString("title"),
                                            news_obj.getString("description"),
                                            news_obj.getString("url"),
                                            news_obj.getString("urlToImage"),
                                            news_obj.getString("publishedAt"),
                                            news_obj.getString("content"),
                                            source.getString("name"));

                                    all_news.add(newsModel);

                                }
                                Log.e("all_news_size", "Size is : " + all_news.size());
                                Log.e("all_news_loop", "The loop is in : " + finalJ);
                            } catch (JSONException e) {
                                Log.e("Volley_data", "JSONException error  : " + e);
                            }

                            allNewsPagerAdapter = new AllNewsPagerAdapter(getSupportFragmentManager(), all_news);
                            allnews_viewpager.setAdapter(allNewsPagerAdapter);
                            allNewsPagerAdapter.notifyDataSetChanged();
                            allnews_title.setText(all_news.size() + " Hot News");
                        }
                    },
                    error -> Log.e("Volley_main_error", "The Main Error is : " + error)) {
                @Override
                public @NotNull Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("User-Agent", "Mozilla/5.0");
                    return headers;
                }
            };
            queue.add(stringRequest);
        }
    }


    @Override
    public void onPositiveButtonClicked(String[] list, ArrayList<String> selectedItemlist) {
        getselectedsource = selectedItemlist;
        allNewsData(getselectedsource);
    }

    @Override
    public void onNegativeButtonClicked() {

    }
}

/*ArrayList<String> sdata= new ArrayList<>();
            sdata.add("dshashudha");
            sdata.add("nsxaij");
            sdata.add("9213nd");

            ArrayList<Boolean> source_boollist = new ArrayList<>();
            for (int n = 0; n < allData.getSource().size(); n++) {
                source_boollist.add(n, sdata.contains(allData.getSource().get(n)));
            }

            boolean[] source_items_boolean_stat= sourceBooleanArrayToPrimitiveArray(source_boollist);
            String[] allsource_stringarray= sdata.toArray(new String[sdata.size()]);

            DialogFragment allsourcechoicedialog = new AllNewsFilterDialogFragment(source_items_boolean_stat, allsource_stringarray);
            allsourcechoicedialog.setCancelable(true);
            allsourcechoicedialog.show(getSupportFragmentManager(), "Multiple Choice Dialog");*/
