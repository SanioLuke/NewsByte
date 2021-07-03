package com.sanioluke00.newsbyte.Models;

import java.util.ArrayList;
import java.util.Random;

public class AllData {

    public final String basic_URL = "https://newsapi.org/v2/";
    public final String apk_key1 = "4229d6a5757c485eb9191f81674b4e45";
    public final String apk_key2 = "ef27f2060b18486db6b1e50f45315c98";

    public String getApiKey() {
        String api_key;
        boolean getranbool = new Random().nextBoolean();
        if (getranbool)
            api_key = apk_key1;
        else
            api_key = apk_key2;

        return api_key;
    }

    public ArrayList<String> getCountryCode() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("in");
        arrayList.add("us");
        arrayList.add("au");
        arrayList.add("ru");
        arrayList.add("fr");
        arrayList.add("gb");
        return arrayList;
    }

    public ArrayList<String> getSource() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("bbc-news");
        arrayList.add("cnn");
        arrayList.add("fox-news");
        arrayList.add("google-news");
        return arrayList;
    }
}



