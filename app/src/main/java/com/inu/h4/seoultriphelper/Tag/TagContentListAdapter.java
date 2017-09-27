package com.inu.h4.seoultriphelper.Tag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.inu.h4.seoultriphelper.R;
import java.util.ArrayList;


public class TagContentListAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<TagContentListItem> listViewItemList = new ArrayList<TagContentListItem>() ;

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tag_content_list_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView sightName = (TextView) convertView.findViewById(R.id.tag_sight_name);
        ImageView sightImage = (ImageView) convertView.findViewById(R.id.tag_sight_image);
        Button getBucketButton = (Button) convertView.findViewById(R.id.btn_tag_get_bucket);
        Button recommendButton = (Button) convertView.findViewById(R.id.btn_tag_recommend);
        Button moreInfoButton = (Button) convertView.findViewById(R.id.btn_tag_more_info);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        TagContentListItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        sightName.setText(listViewItem.getSightName());
        sightImage.setImageResource(listViewItem.getImage());
        getBucketButton = listViewItem.getGetBucketButton();
        recommendButton = listViewItem.getRecommendButton();
        moreInfoButton = listViewItem.getMoreInfoButton();

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(TagContentListItem item) {
        listViewItemList.add(item);
    }

    // todo : 목록 갱신을 위해 내용을 비움. DB 연동하게되면 필요없어지므로 삭제.
    public void removeItem() { listViewItemList.clear(); }
}
