package com.inu.h4.seoultriphelper.Tag;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.inu.h4.seoultriphelper.DataBase.SIGHT1000ARRAY;
import com.inu.h4.seoultriphelper.DataBase.SIGHT1000_LIST;
import com.inu.h4.seoultriphelper.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TagSelectedSightFragment extends Fragment {
    static TagSelectedSightListViewAdapter adapter;
    List<String> sightIdList;
    List<SIGHT1000_LIST> sight1000List;

    static int synk = 0;
    private static final String SERVER_IP = "http://52.42.208.72/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("추천 여행지");
        View layout = inflater.inflate(R.layout.tag_selected_sight, container, false);

        //Bundle bundle = getArguments();
        Bundle bundle = new Bundle();
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("5");
        bundle.putStringArrayList("sightList", (ArrayList<String>)list);

        if(bundle != null) {
            sightIdList = (ArrayList<String>) bundle.get("sightList");
            sight1000List = new ArrayList<>();
            synk = 1;

            adapter = new TagSelectedSightListViewAdapter(this);

            loadSight1000Data(sightIdList);
            convertSIght1000DataToListViewItem(sight1000List);

            ListView listView = (ListView) layout.findViewById(R.id.tag_selected_sight_list_view);
            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        }
        return layout;
    }

    // 아이디 목록으로 관광지 정보들을 받아 옴.
    public void loadSight1000Data(List<String> sightIdList) {
        for(int i=0; i<sightIdList.size(); i++) {
            SIGHT1000_LIST item = SIGHT1000ARRAY.getItemById(sightIdList.get(i));
            sight1000List.add(item);
        }
    }

    public void convertSIght1000DataToListViewItem(List<SIGHT1000_LIST> sight1000List) {
        for(int i=0; i<sight1000List.size(); i++) {
            SIGHT1000_LIST sightItem = sight1000List.get(i);
            TagSelectedSightListViewItem item = new TagSelectedSightListViewItem();
            item.setPlaceId(sightItem.getData(0));
            item.setSightName(sightItem.getData(1));
            item.setRecommendCount(Integer.valueOf(sightItem.getData(3)));
            if(!sightItem.getData(10).equals("0")) {
                double dAvgPoint = Math.round(Double.valueOf(
                        sightItem.getData(9)) / Double.valueOf(sightItem.getData(10)
                ) * 10d) / 10d;
                item.setRating(dAvgPoint);
            }
            LoadBitmapfromUrl(sightItem.getData(8),item);
            Log.d("LOG/PREFER_SIGHT",item.getSightName());
            adapter.addItem(item);
        }
    }
    // Uri -> 비트맵으로의 전환 메서드.
    public static void LoadBitmapfromUrl(final String uri, final TagSelectedSightListViewItem item) {
        class LoadClass extends AsyncTask< Object, Void, Bitmap> {
            @Override
            protected Bitmap doInBackground( Object... params ) {
                String uri = (String) params[0];
                return loadBitmap( uri );
            }

            @Override
            protected void onPostExecute( Bitmap result ) {
                if(result != null) {
                    item.setImageBitmap(result);
                    refresh();
                }
            }

            public Bitmap loadBitmap( String uri ) {
                if(synk == 1) {
                    Bitmap bitmap = null;
                    URL newurl = null;
                    bitmap = null;
                    try {
                        newurl = new URL(SERVER_IP.concat(uri));
                        bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                    Log.d("LOG/MONTH", "Get Recommend Sight Bitmap! " + uri);

                    return bitmap;
                } else
                    return null;
            }
        }
        LoadClass inner = new LoadClass();
        inner.execute(uri);
    }

    public static void refresh(){
        adapter.notifyDataSetChanged();
        Log.d("LOG/MONTH", "Refresh");
    }

    @Override
    public void onStop() {
        super.onStop();
        synk = 0;
    }
}