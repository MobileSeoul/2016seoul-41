package com.inu.h4.seoultriphelper.Bucket;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.inu.h4.seoultriphelper.Detail.SightDetailFragment;
import com.inu.h4.seoultriphelper.InnerDBHelper;
import com.inu.h4.seoultriphelper.R;

import java.util.ArrayList;

public class BucketGridViewAdapter extends BaseAdapter {
    BucketGridViewAdapter adapter = this;

    Fragment fragment;
    public BucketGridViewAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<BucketGridViewItem> GridViewItemList = new ArrayList<>();

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return GridViewItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        final InnerDBHelper InnerDBHelper = new InnerDBHelper(context, "BUCKETDB1.db",null,1);

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.bucket_list_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView recommend = (TextView) convertView.findViewById(R.id.bucket_list_recommend);
        TextView sightName = (TextView) convertView.findViewById(R.id.bucket_list_name);
        ImageView sightImage = (ImageView) convertView.findViewById(R.id.bucket_list_image);
        // 제거용 버튼
        ImageButton removeBtn = (ImageButton)  convertView.findViewById(R.id.bucket_remove_btn);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final BucketGridViewItem GridViewItem = GridViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        recommend.setText(String.valueOf("추천 " + GridViewItem.getRecommend()));
        sightName.setText(GridViewItem.getSightName());
        sightImage.setImageBitmap(GridViewItem.getBitmap());
        //버튼 클릭하면 삭제

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GridViewItemList.remove(pos);
                adapter.notifyDataSetChanged();
                Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                InnerDBHelper.delete(String.valueOf(GridViewItem.getId()));
            }

        });

        sightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment sightDetailFragment = new SightDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("placeId", GridViewItem.getId());
                sightDetailFragment.setArguments(bundle);
                fragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, sightDetailFragment).addToBackStack(null).commit();
            }
        });


        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return GridViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(BucketGridViewItem item) {
        GridViewItemList.add(item);
    }

}