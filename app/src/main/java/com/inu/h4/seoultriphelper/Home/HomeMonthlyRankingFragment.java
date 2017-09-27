package com.inu.h4.seoultriphelper.Home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.inu.h4.seoultriphelper.DataBase.InsertDB_REVIEW1000;
import com.inu.h4.seoultriphelper.DataBase.SIGHT1000ARRAY;
import com.inu.h4.seoultriphelper.DataBase.SIGHT1000_LIST;
import com.inu.h4.seoultriphelper.DataBase.SIGHT1100ForDetailImage;
import com.inu.h4.seoultriphelper.MainActivity;
import com.inu.h4.seoultriphelper.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeMonthlyRankingFragment extends Fragment {
    private ListView listView;
    private ArrayList<HomeRankingListViewItem> data;
    private static HomeRankingListViewAdapter adapter;
    private static int synk;
    private static int checker;

    private static final String SERVER_IP = "http://52.42.208.72/";

    @Override
    public void onStart() {
        super.onStart();
        Log.d("LOG/MONTH", "onStart()");
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
        Log.d("LOG/MONTH", "onCreatedView()");
        checker = 0;

        View layout = inflater.inflate(R.layout.home_monthly_ranking, container, false);
        adapter = new HomeRankingListViewAdapter(this,0);

        listView = (ListView) layout.findViewById(R.id.home_monthly_ranking_list_view);
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
                    if(synk == 2) {
                        synk = 1;
                        Log.d("LOG/MONTH", String.valueOf(SIGHT1000ARRAY.sight1000Array.size()));
                        if (HomeFragment.monthFragmentRowCount + 4 <= SIGHT1000ARRAY.sight1000Array.size()) {
                            HomeFragment.monthFragmentRowCount += 4;

                            Log.d("LOG/MONTH", String.valueOf(adapter.listViewItemList.size()));
                            SIGHT1000ARRAY.Monthsorting();
                            for (int i = HomeFragment.monthFragmentRowCount - 4; i < HomeFragment.monthFragmentRowCount; i++) {
                                HomeRankingListViewItem item = new HomeRankingListViewItem();
                                //Log.d("LOG/MONTH", "beforeGetData");
                                SIGHT1000ARRAY.getMonthArrayData(item, i);
                                LoadBitmapfromUrl(SIGHT1000ARRAY.sight1000Array.get(i).getData(8), item);
                                //Log.d("LOG/MONTH", "GetData : " + item.getSightName());
                                data.add(item);
                                adapter.addItem(data.get(i));
                            }
                        } else if (SIGHT1000ARRAY.sight1000Array.size() != HomeFragment.monthFragmentRowCount) {
                            int sub = SIGHT1000ARRAY.sight1000Array.size() - HomeFragment.monthFragmentRowCount;
                            HomeFragment.monthFragmentRowCount = SIGHT1000ARRAY.sight1000Array.size();

                            Log.d("LOG/MONTH", String.valueOf(adapter.listViewItemList.size()));
                            SIGHT1000ARRAY.Monthsorting();
                            for (int i = HomeFragment.monthFragmentRowCount; i < HomeFragment.monthFragmentRowCount + sub; i++) {
                                HomeRankingListViewItem item = new HomeRankingListViewItem();
                                //Log.d("LOG/MONTH", "beforeGetData");
                                SIGHT1000ARRAY.getMonthArrayData(item, i);
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
        data = new ArrayList<>();
        synk = 1;
        SIGHT1000ARRAY.Monthsorting();
        for(int i = 0; i< HomeFragment.monthFragmentRowCount; i++) {
            HomeRankingListViewItem item = new HomeRankingListViewItem();
            //Log.d("LOG/MONTH", "beforeGetData");
            SIGHT1000ARRAY.getMonthArrayData(item, i);
            LoadBitmapfromUrl(SIGHT1000ARRAY.sight1000Array.get(i).getData(8), item);
            //Log.d("LOG/MONTH", "GetData : " + item.getSightName());
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
                    if(checker == HomeFragment.monthFragmentRowCount)
                        synk = 2;
                    Log.d("LOG/MONTH", "Get Home Bitmap! " + uri);

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
}