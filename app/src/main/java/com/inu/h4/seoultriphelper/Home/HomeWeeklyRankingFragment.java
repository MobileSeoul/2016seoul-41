package com.inu.h4.seoultriphelper.Home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.inu.h4.seoultriphelper.DataBase.SIGHT1000ARRAY;
import com.inu.h4.seoultriphelper.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HomeWeeklyRankingFragment extends Fragment {
    private ListView listView;
    private ArrayList<HomeRankingListViewItem> data;
    private static HomeRankingListViewAdapter adapter;
    private static int synk;
    private static int checker;

    private static final String SERVER_IP = "http://52.42.208.72/";

    @Override
    public void onStart() {
        super.onStart();
        Log.d("LOG/WEEK", "onStart()");
    }

    @Override
    public void onStop() {
        super.onStop();
        synk = 0;
        checker = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("LOG/WEEK", "onCreateView()");
        checker = 0;

        View layout = inflater.inflate(R.layout.home_weekly_ranking, container, false);
        adapter = new HomeRankingListViewAdapter(this,1);

        listView = (ListView) layout.findViewById(R.id.home_weekly_ranking_list_view);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            boolean lastItemVisibleFlag = false;        //화면에 리스트의 마지막 아이템이 보여지는지 체크

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //현재 화면에 보이는 첫번째 리스트 아이템의 번호(firstVisibleItem) + 현재 화면에 보이는 리스트 아이템의 갯수(visibleItemCount)가 리스트 전체의 갯수(totalItemCount) -1 보다 크거나 같을때
                lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
            }
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //OnScrollListener.SCROLL_STATE_IDLE은 스크롤이 이동하다가 멈추었을때 발생되는 스크롤 상태입니다.
                //즉 스크롤이 바닦에 닿아 멈춘 상태에 처리를 하겠다는 뜻
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag) {
                    if (synk == 2) {
                        synk = 1;
                        Log.d("LOG/WEEK", String.valueOf(SIGHT1000ARRAY.sight1000Array.size()));
                        if (HomeFragment.weekFragmentRowCount + 4 <= SIGHT1000ARRAY.sight1000Array.size()) {
                            HomeFragment.weekFragmentRowCount += 4;

                            Log.d("LOG/WEEK", String.valueOf(adapter.listViewItemList.size()));
                            SIGHT1000ARRAY.Weeksorting();
                            for (int i = HomeFragment.weekFragmentRowCount - 4; i < HomeFragment.weekFragmentRowCount; i++) {
                                HomeRankingListViewItem item = new HomeRankingListViewItem();
                                //Log.d("LOG/MONTH", "beforeGetData");
                                SIGHT1000ARRAY.getWeekArrayData(item, i);
                                LoadBitmapfromUrl(SIGHT1000ARRAY.sight1000Array.get(i).getData(8), item);
                                //Log.d("LOG/MONTH", "GetData : " + item.getSightName());
                                data.add(item);
                                adapter.addItem(data.get(i));
                            }
                        } else if (SIGHT1000ARRAY.sight1000Array.size() != HomeFragment.weekFragmentRowCount) {
                            int sub = SIGHT1000ARRAY.sight1000Array.size() - HomeFragment.weekFragmentRowCount;
                            HomeFragment.weekFragmentRowCount = SIGHT1000ARRAY.sight1000Array.size();

                            Log.d("LOG/WEEK", String.valueOf(adapter.listViewItemList.size()));
                            SIGHT1000ARRAY.Weeksorting();
                            for (int i = HomeFragment.weekFragmentRowCount; i < HomeFragment.weekFragmentRowCount + sub; i++) {
                                HomeRankingListViewItem item = new HomeRankingListViewItem();
                                //Log.d("LOG/MONTH", "beforeGetData");
                                SIGHT1000ARRAY.getWeekArrayData(item, i);
                                LoadBitmapfromUrl(SIGHT1000ARRAY.sight1000Array.get(i).getData(8), item);
                                //Log.d("LOG/MONTH", "GetData : " + item.getSightName());
                                data.add(item);
                                adapter.addItem(data.get(i));
                            }
                        }
                    }
                }
            }
        });

        getData();
        refresh();

        return layout;
    }

    public void getData(){
        synk = 1;
        data = new ArrayList<>();
        HomeRankingListViewItem item;
        SIGHT1000ARRAY.Weeksorting();
        for(int i = 0; i< HomeFragment.weekFragmentRowCount; i++) {
            item = new HomeRankingListViewItem();
            //Log.d("LOG/WEEK", "beforeGetData");
            SIGHT1000ARRAY.getWeekArrayData(item, i);
            LoadBitmapfromUrl(SIGHT1000ARRAY.sight1000Array.get(i).getData(8), item);
            //Log.d("LOG/WEEK", "GetData : " + item.getSightName());
            data.add(item);
            adapter.addItem(data.get(i));
        }
    }
    // Uri -> 비트맵으로의 전환 메서드.
    public static void LoadBitmapfromUrl(final String uri, final HomeRankingListViewItem item) {
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

                    checker++;
                    if(checker == HomeFragment.weekFragmentRowCount)
                        synk = 2;
                    Log.d("LOG/WEEK", "Get Home Bitmap! " + uri);

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
        Log.d("LOG/WEEK", "Refresh");
    }
}