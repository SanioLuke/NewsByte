package com.sanioluke00.newsbyte.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.sanioluke00.newsbyte.Adapters.SearchResultAdapter;
import com.sanioluke00.newsbyte.Adapters.TopHeadlineAdapter;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final ArrayList<NewsModel> all_search_news = new ArrayList<>();
    RecyclerView top_headline_rec, home_search_rec;
    TopHeadlineAdapter topHeadlineAdapter;
    TextView search_news_nodatatxt, home_showmore_btn;
    ImageButton saved_news_btn;
    Toolbar home_toolbar;
    EditText home_search_bar;
    ImageButton home_search_btn;
    View home_search_resultlay;
    SearchResultAdapter searchResultAdapter;
    AllData allData = new AllData();
    private DrawerLayout home_drawer;
    private NavigationView homemain_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        top_headline_rec = findViewById(R.id.top_headline_rec);
        home_search_bar = findViewById(R.id.home_search_bar);
        home_search_btn = findViewById(R.id.home_search_btn);
        saved_news_btn = findViewById(R.id.saved_news_btn);
        home_toolbar = findViewById(R.id.home_toolbar);
        home_drawer = findViewById(R.id.home_drawer);
        homemain_nav = findViewById(R.id.homemain_nav);
        home_showmore_btn = findViewById(R.id.home_showmore_btn);
        home_search_resultlay = findViewById(R.id.home_search_resultlay);
        home_search_rec = findViewById(R.id.home_search_rec);
        search_news_nodatatxt = findViewById(R.id.search_news_nodatatxt);

        homemain_nav.setNavigationItemSelectedListener(MainActivity.this);
        homemain_nav.setCheckedItem(R.id.nav_home);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, home_drawer, home_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        home_drawer.addDrawerListener(toggle);
        toggle.syncState();

        getLatestNewsData(allData.basic_URL + "top-headlines?country=in&apiKey=" + allData.getApiKey(), false);

        home_showmore_btn.setOnClickListener(v -> {
            home_showmore_btn.setVisibility(View.GONE);
            getLatestNewsData(allData.basic_URL + "top-headlines?country=in&pageSize=100&apiKey=" + allData.getApiKey(), true);
        });

        home_search_btn.setOnClickListener(v -> {
            String search_txt = home_search_bar.getText().toString();
            if (search_txt.equals("") || search_txt.isEmpty() || search_txt.trim().length() == 0) {
                home_search_resultlay.setVisibility(View.GONE);
            } else {
                home_search_resultlay.setVisibility(View.VISIBLE);
                getSearchNewsData(search_txt);
            }
        });

        saved_news_btn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SavedNewsActivity.class)));

        home_search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String result = home_search_bar.getText().toString();
                if (result.equals("") || result.isEmpty() || result.trim().length() == 0) {
                    home_search_resultlay.setVisibility(View.GONE);
                    search_news_nodatatxt.setVisibility(View.GONE);
                    home_search_rec.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void getLatestNewsData(String url, boolean fnbool) {

        ArrayList<NewsModel> all_latest_news = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
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

                    all_latest_news.add(newsModel);

                    {
                        Log.e("author", "" + news_obj.getString("author"));
                        Log.e("title", "" + news_obj.getString("title"));
                        Log.e("description", "" + news_obj.getString("description"));
                        Log.e("url", "" + news_obj.getString("url"));
                        Log.e("urlToImage", "" + news_obj.getString("urlToImage"));
                        Log.e("publishedAt", "" + news_obj.getString("publishedAt"));
                        Log.e("content", "" + news_obj.getString("content"));
                    } //Logs

                }

                if (fnbool)
                    Collections.reverse(all_latest_news);

                topHeadlineAdapter = new TopHeadlineAdapter(getApplicationContext(), all_latest_news);
                top_headline_rec.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                top_headline_rec.setAdapter(topHeadlineAdapter);
                topHeadlineAdapter.notifyDataSetChanged();

                Log.e("all_news_size", "Size is : " + all_latest_news.size());
            } catch (JSONException e) {
                Log.e("Volley_data", "JSONException error  : " + e);
            }
        }, error -> Log.e("Volley_main_error", "The Main Error is : " + error)) {
            @Override
            public @NotNull Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0");
                return headers;
            }
        };
        queue.add(stringRequest);

    }

    private void getSearchNewsData(String search_txt) {

        if (all_search_news.size() > 0) {
            all_search_news.clear();
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, allData.basic_URL + "everything?qInTitle=" + search_txt + "&apiKey=" + allData.getApiKey(), response -> {
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

                    all_search_news.add(newsModel);

                    {
                        Log.e("author", "" + news_obj.getString("author"));
                        Log.e("title", "" + news_obj.getString("title"));
                        Log.e("description", "" + news_obj.getString("description"));
                        Log.e("url", "" + news_obj.getString("url"));
                        Log.e("urlToImage", "" + news_obj.getString("urlToImage"));
                        Log.e("publishedAt", "" + news_obj.getString("publishedAt"));
                        Log.e("content", "" + news_obj.getString("content"));
                    } //Logs

                }

                home_search_resultlay.setVisibility(View.VISIBLE);
                if (all_search_news.size() == 0) {
                    search_news_nodatatxt.setVisibility(View.VISIBLE);
                    home_search_rec.setVisibility(View.GONE);
                } else {
                    search_news_nodatatxt.setVisibility(View.GONE);
                    home_search_rec.setVisibility(View.VISIBLE);
                    searchResultAdapter = new SearchResultAdapter(getApplicationContext(), all_search_news);
                    home_search_rec.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    home_search_rec.setAdapter(searchResultAdapter);
                    searchResultAdapter.notifyDataSetChanged();
                }

                Log.e("all_news_size", "Size is : " + all_search_news.size());
            } catch (JSONException e) {
                Log.e("Volley_data", "JSONException error  : " + e);
            }
        }, error -> Log.e("Volley_main_error", "The Main Error is : " + error)) {
            @Override
            public @NotNull Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0");
                return headers;
            }
        };
        queue.add(stringRequest);

    }

    @Override
    protected void onResume() {
        super.onResume();
        home_search_bar.setText("");
        all_search_news.clear();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                new Handler().postDelayed(() -> home_drawer.closeDrawers(), 300);
                break;

            case R.id.nav_noti:
                new Handler().postDelayed(() -> {
                    home_drawer.closeDrawers();
                    homemain_nav.setCheckedItem(R.id.nav_home);
                }, 400);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), AllNewsActivity.class));
                    }
                }, 700);
                break;

            case R.id.nav_settings:
                new Handler().postDelayed(() -> {
                    home_drawer.closeDrawers();
                    Toast.makeText(this, "   Settings   ", Toast.LENGTH_SHORT).show();
                }, 200);
                break;

            case R.id.nav_email:
                new Handler().postDelayed(() -> {
                    home_drawer.closeDrawers();
                    homemain_nav.setCheckedItem(R.id.nav_home);
                    String mail_id = "newsbyte00@gmail.com";
                    Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + mail_id));
                    String sub = "Correction for the App";
                    String body_1 = "The App is showing the some bugs and problems and causing errors in the display. Please look through the bugs and fix this ASAP..\nhttps://play.google.com/store/apps/details?id=com.solutions.ncertbooks";
                    intent2.putExtra(Intent.EXTRA_SUBJECT, sub);
                    intent2.putExtra(Intent.EXTRA_TEXT, body_1);
                    startActivity(intent2);
                }, 300);
                break;

            case R.id.nav_share:
                home_drawer.closeDrawers();
                homemain_nav.setCheckedItem(R.id.nav_home);
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.setType("text/plain");
                String body = "Let me recommend you this application\n\nhttps://play.google.com/store/apps/details?id=com.sanioluke.newsbyte";
                intent1.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(intent1, "Share using"));
                break;

            case R.id.nac_rate:
                new Handler().postDelayed(() -> {
                    home_drawer.closeDrawers();
                    homemain_nav.setCheckedItem(R.id.nav_home);
                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.sanioluke.newsbyte"));
                    startActivity(in);
                }, 400);
                break;

            default:
                break;
        }
        return true;
    }
}
