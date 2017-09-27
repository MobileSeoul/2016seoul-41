package com.inu.h4.seoultriphelper.Search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inu.h4.seoultriphelper.R;

public class SearchFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LOG/SEARCH", "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("LOG/SEARCH", "onCreateView()");
        View layout = inflater.inflate(R.layout.search, container, false);



        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("LOG/SEARCH", "onStart()");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("LOG/SEARCH", "onViewCreated()");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("LOG/SEARCH", "onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        Log.d("LOG/SEARCH", "onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("LOG/SEARCH", "onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("LOG/SEARCH", "onStop()");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d("LOG/SEARCH", "onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("LOG/SEARCH", "onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d("LOG/SEARCH", "onDetach()");
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("LOG/SEARCH", "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }
}