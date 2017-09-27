package com.inu.h4.seoultriphelper.Planner;

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
import com.inu.h4.seoultriphelper.Prefer.PreferRecommendSightListViewItem;
import com.inu.h4.seoultriphelper.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PlannerExistFragment extends Fragment {

    PlannerDB plannerDB;
    private ListView listView;
    private ArrayList<PlannerListViewItem> data;
    private static PlannerListViewAdapter adapter;
    static int synk;

    private static final String SERVER_IP = "http://52.42.208.72/";

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        getActivity().setTitle("여행 플래너");
        plannerDB = new PlannerDB(getActivity(), "planner.db", null, 1);
        View layout = inflater.inflate(R.layout.planner, container, false);
        adapter = new PlannerListViewAdapter(this, plannerDB);
        synk = 1;

        listView = (ListView) layout.findViewById(R.id.PlannerListView);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        getData();
        refresh();

        for(int i=0; i<adapter.getCount(); i++) {
            PlannerListViewItem item = (PlannerListViewItem) adapter.getItem(i);
            String sightName = item.getP_name();
            SIGHT1000_LIST sightItem = SIGHT1000ARRAY.getItemByName(sightName);
            LoadBitmapfromUrl(sightItem.getData(8), (PlannerListViewItem) adapter.getItem(i));

        }

        return layout;
    }

    public void getData(){
        plannerDB = new PlannerDB(getActivity(), "planner.db", null, 1);
        plannerDB.DBSelect(data, adapter);
    }

    public static void refresh(){
        adapter.notifyDataSetChanged();
    }

    // Uri -> 비트맵으로의 전환 메서드.
    public static void LoadBitmapfromUrl(final String uri, final PlannerListViewItem item) {
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
}
