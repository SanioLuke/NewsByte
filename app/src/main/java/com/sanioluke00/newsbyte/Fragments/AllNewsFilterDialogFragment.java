package com.sanioluke00.newsbyte.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class AllNewsFilterDialogFragment extends DialogFragment {

    private final String[] source_list = new String[]{"bbc-news", "cnn", "fox-news", "google-news"};
    private final String[] source_names = new String[]{"#BBC-News", "#CNN", "#Fox-News", "#Google-News"};
    private final ArrayList<String> selected_source_array = new ArrayList<>();
    private final ArrayList<String> source_array;
    private final boolean[] selected_source_list;
    onMultiChoiceListner multiChoiceListner;

    public AllNewsFilterDialogFragment(boolean[] selected_source_list, ArrayList<String> source_array) {
        this.selected_source_list = selected_source_list;
        this.source_array = source_array;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            multiChoiceListner = (onMultiChoiceListner) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString() + "onMultiChoiceListner must be implemented");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        ArrayList<String> all_prefs_array = new ArrayList<>(Arrays.asList(source_list));
        for (int ch1 = 0; ch1 < all_prefs_array.size(); ch1++) {
            for (int ch2 = 0; ch2 < source_array.size(); ch2++) {
                if (all_prefs_array.get(ch1).equals(source_array.get(ch2))) {
                    selected_source_array.add(source_list[ch1]);
                }
            }
        }

        builder.setTitle("Filter HotNews Platforms")
                .setMultiChoiceItems(source_names, selected_source_list, (dialogInterface, which, isChecked) -> {

                    if (isChecked) {
                        selected_source_array.add(source_list[which]);
                    } else {
                        selected_source_array.remove(source_list[which]);
                    }

                    Button positive = ((AlertDialog) dialogInterface).getButton(AlertDialog.BUTTON_POSITIVE);
                    positive.setEnabled(selected_source_array.size() > 0);
                })
                .setPositiveButton("Select", (dialog, which) -> multiChoiceListner.onPositiveButtonClicked(source_list, selected_source_array))
                .setNegativeButton("Cancel", (dialog, which) -> multiChoiceListner.onNegativeButtonClicked());

        return builder.create();

    }

    public interface onMultiChoiceListner {
        void onPositiveButtonClicked(String[] list, ArrayList<String> selectedItemlist);

        void onNegativeButtonClicked();
    }
}
