package com.inu.h4.seoultriphelper.Bucket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inu.h4.seoultriphelper.R;

public class BucketEmptyFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("버킷리스트");

        return inflater.inflate(R.layout.bucket_empty, container, false);
    }
}
