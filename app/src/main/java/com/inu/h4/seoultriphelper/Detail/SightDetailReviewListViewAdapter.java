package com.inu.h4.seoultriphelper.Detail;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.inu.h4.seoultriphelper.R;

import java.util.ArrayList;

public class SightDetailReviewListViewAdapter extends BaseAdapter {
    private ArrayList<SightDetailReviewListViewItem> mListData = new ArrayList<>();

    @Override
    public int getCount() {
        return mListData.size();
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return mListData.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.detail_review_list_item, null);
        }

        TextView writer = (TextView) convertView.findViewById(R.id.detail_review_writer);
        RatingBar rating = (RatingBar) convertView.findViewById(R.id.detail_review_rating);
        TextView info = (TextView) convertView.findViewById(R.id.detail_review_info);
        TextView date = (TextView) convertView.findViewById(R.id.detail_review_date);

        SightDetailReviewListViewItem item = mListData.get(pos);

        writer.setText(item.getWriter());
        rating.setRating(item.getRecommendRating());
        info.setText(item.getInfo());
        date.setText(item.getDate());

        return convertView;
    }
    public void addItem(SightDetailReviewListViewItem item) {
        mListData.add(item);
    }
    public void initListData() {
        mListData.clear();
    }
}