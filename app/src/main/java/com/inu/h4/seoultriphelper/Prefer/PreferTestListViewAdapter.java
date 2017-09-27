package com.inu.h4.seoultriphelper.Prefer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.inu.h4.seoultriphelper.R;

import java.util.ArrayList;

public class PreferTestListViewAdapter extends BaseAdapter {
    private ArrayList<PreferTestListViewItem> mListData = new ArrayList<>();
    private ArrayList<Integer> checker = new ArrayList<>();
    private ArrayList<Integer> radioValueList = new ArrayList<>();

    public void initSubList() {
        for(int i=0; i<mListData.size(); i++) {
            checker.add(i,0);
            radioValueList.add(i,0);
        }
    }

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
            convertView = inflater.inflate(R.layout.prefer_test_item, null);
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.prefer_test_very_yes:
                        radioValueList.set(pos, 5);
                        break;
                    case R.id.prefer_test_little_yes:
                        radioValueList.set(pos, 4);
                        break;
                    case R.id.prefer_test_soso:
                        radioValueList.set(pos, 3);
                        break;
                    case R.id.prefer_test_little_no:
                        radioValueList.set(pos, 2);
                        break;
                    case R.id.prefer_test_very_no:
                        radioValueList.set(pos, 1);
                        break;
                }
                checker.set(pos, v.getId());
            }
        };

        TextView tv = (TextView) convertView.findViewById(R.id.prefer_test_text);
        RadioGroup rg = (RadioGroup) convertView.findViewById(R.id.radio_group);
        RadioButton rb1 = (RadioButton) convertView.findViewById(R.id.prefer_test_very_yes);
        rb1.setOnClickListener(listener);

        RadioButton rb2 = (RadioButton) convertView.findViewById(R.id.prefer_test_little_yes);
        rb2.setOnClickListener(listener);

        RadioButton rb3 = (RadioButton) convertView.findViewById(R.id.prefer_test_soso);
        rb3.setOnClickListener(listener);

        RadioButton rb4 = (RadioButton) convertView.findViewById(R.id.prefer_test_little_no);
        rb4.setOnClickListener(listener);

        RadioButton rb5 = (RadioButton) convertView.findViewById(R.id.prefer_test_very_no);
        rb5.setOnClickListener(listener);

        PreferTestListViewItem mData = mListData.get(pos);

        tv.setText(mData.getText());

        if(checker.get(pos) != 0)
            rg.check(checker.get(pos));
        else
            rg.clearCheck();

        return convertView;
    }

    public void addItem(String text){
        PreferTestListViewItem addInfo = new PreferTestListViewItem();
        addInfo.setText(text);

        mListData.add(addInfo);
    }

    // 선택된 radioButton들의 값을 합산.
    public int getRadioValues() {
        int sum = 0;
        for(int i=0;i<radioValueList.size();i++) {
            sum += radioValueList.get(i);
        }
        return sum;
    }

    // 선택된 radioButton들을 모두 초기화시킴.
    public void clearRadio() {
        for(int i=0; i<mListData.size(); i++) {
            checker.set(i,0);
            radioValueList.set(i,0);
        }
        notifyDataSetChanged();
    }

    // radioButton을 전부 선택했는지 확인.
    public boolean isFilledOut() {
        for(int i=0; i<radioValueList.size(); i++) {
            if(radioValueList.get(i) == 0)
                return true;
        }
        return true;
    }
}