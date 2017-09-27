package com.inu.h4.seoultriphelper.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inu.h4.seoultriphelper.R;
import com.inu.h4.seoultriphelper.SmartTabLayoutLibrary.FragmentPagerItem;
import com.inu.h4.seoultriphelper.SmartTabLayoutLibrary.FragmentPagerItems;
import com.inu.h4.seoultriphelper.SmartTabLayoutLibrary.FragmentStatePagerItemAdapter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class HomeFragment extends Fragment {
    public static int monthFragmentRowCount, weekFragmentRowCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LOG/PAGE_HOME", "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("LOG/PAGE_HOME", "onCreateView()");
        monthFragmentRowCount = 4;
        weekFragmentRowCount = 4;
        View layout = inflater.inflate(R.layout.home, container, false);

        FragmentStatePagerItemAdapter adapter = new FragmentStatePagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getActivity())
                .add(R.string.tab_title_month, HomeMonthlyRankingFragment.class)
                .add(R.string.tab_title_week, HomeWeeklyRankingFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) layout.findViewById(R.id.home_viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) layout.findViewById(R.id.home_viewpagertab);
        viewPagerTab.setViewPager(viewPager);

        getActivity().setTitle("홈");
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("LOG/PAGE_HOME", "onStart()");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("LOG/PAGE_HOME", "onViewCreated()");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("LOG/PAGE_HOME", "onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        Log.d("LOG/PAGE_HOME", "onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("LOG/PAGE_HOME", "onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("LOG/PAGE_HOME", "onStop()");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d("LOG/PAGE_HOME", "onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("LOG/PAGE_HOME", "onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d("LOG/PAGE_HOME", "onDetach()");
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("LOG/PAGE_HOME", "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }
}