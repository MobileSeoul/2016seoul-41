package com.inu.h4.seoultriphelper.Search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.inu.h4.seoultriphelper.Detail.SightDetailFragment;
import com.inu.h4.seoultriphelper.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SearchListViewAdapter extends BaseAdapter {
    // Declare Variables
    Context context;
    LayoutInflater inflater;

    // 검색데이터 저장과 출력을 위한 리스트 변수들
    private ArrayList<SearchListViewItem> arrayList; // 데이터 저장소
    private List<SearchListViewItem> SearchListViewItemList = null; // 출력용 목록

    // 화면 전환용 변수들
    private AppCompatActivity activity;
    private FragmentTransaction ft;

    // 검색 종료 후 SearchView를 닫기 위한 변수
    private MenuItem mMenuItem;

    public SearchListViewAdapter(AppCompatActivity activity) {
        this.activity = activity;
        this.SearchListViewItemList = new ArrayList<>();
        this.arrayList = new ArrayList<>();
    }

    public void setMenuItem(MenuItem item) { mMenuItem = item; }

    public class ViewHolder {
        TextView tv_name;
    }

    @Override
    public int getCount() {
        return SearchListViewItemList.size();
    }

    @Override
    public SearchListViewItem getItem(int position) {
        return SearchListViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        final SearchListViewItem SearchListViewItem = SearchListViewItemList.get(position);

        if (view == null) {
            this.context = parent.getContext();
            inflater = LayoutInflater.from(context);

            holder = new ViewHolder();
            view = inflater.inflate(R.layout.search_item_listview, null);

            // Locate the TextViews in listview_item.xml
            holder.tv_name = (TextView) view.findViewById(R.id.search_text);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.tv_name.setText(SearchListViewItem.getName());

        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View item) {
                Fragment sightDetailFragment = new SightDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("placeId", SearchListViewItem.getPlaceId());
                sightDetailFragment.setArguments(bundle);

                String fragmentTag = activity.getSupportFragmentManager()
                                .getBackStackEntryAt(activity.getSupportFragmentManager()
                                .getBackStackEntryCount() - 1).getName();

                ft = activity.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, sightDetailFragment).addToBackStack(fragmentTag).commit();

                // 검색 결과 화면을 가리면서 search view도 닫는다.
                activity.findViewById(R.id.search_result).setVisibility(View.GONE);
                mMenuItem.collapseActionView();
            }
        });

        return view;
    }

    public void addItem(SearchListViewItem item) { arrayList.add(item); }

    // 자동완성 기능을 담당하는 메소드
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        SearchListViewItemList.clear();
        if (charText.length() == 0) {
            SearchListViewItemList.addAll(arrayList);
        } else {
            for (SearchListViewItem SearchListViewItem : arrayList) {
                String name = SearchListViewItem.getName();
                if (name.toLowerCase().contains(charText)) {
                    SearchListViewItemList.add(SearchListViewItem);
                }
            }
        }
        notifyDataSetChanged();
    }
}