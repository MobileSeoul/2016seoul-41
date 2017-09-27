package com.inu.h4.seoultriphelper.Planner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inu.h4.seoultriphelper.R;

public class PlannerEmptyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.planner_empty, container, false);
        getActivity().setTitle("여행 플래너");

        return layout;
    }
}
