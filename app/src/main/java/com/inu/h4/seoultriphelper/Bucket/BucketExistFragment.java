package com.inu.h4.seoultriphelper.Bucket;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.inu.h4.seoultriphelper.DataBase.SIGHT1000ARRAY;
import com.inu.h4.seoultriphelper.DataBase.SIGHT1000_LIST;
import com.inu.h4.seoultriphelper.InnerDBHelper;
import com.inu.h4.seoultriphelper.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BucketExistFragment extends Fragment {
    private GridView GridView;
    private ArrayList<BucketGridViewItem> data;
    private static BucketGridViewAdapter adapter;
    private static int synk;
    private static final String SERVER_IP = "http://52.42.208.72/";
    private RelativeLayout BucketMapViewContainer;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        synk = 1;
        //디비호출 디비는 같은 폰에 하나가 아니라 앱에 하나임
        final InnerDBHelper InnerDBHelper = new InnerDBHelper(getActivity(), "BUCKETDB1.db", null, 1);
        getActivity().setTitle("버킷리스트");

        View layout = inflater.inflate(R.layout.bucket_list, container, false);
        adapter = new BucketGridViewAdapter(this);

        GridView = (GridView) layout.findViewById(R.id.bucket_list_grid_view);
        GridView.setAdapter(adapter);
        GridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        loadData();
        /////////////////////////////////////////////////////////////////////////////
        MapView mapView = new MapView(getActivity());
        mapView.setDaumMapApiKey("753615f093d763e50b6a87a0a0f25f05");

        BucketMapViewContainer = (RelativeLayout) layout.findViewById(R.id.bucket_list_mapView);
        BucketMapViewContainer.addView(mapView);
        mapView.setZoomLevel(7, true);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.553161, 126.972664), true);

        MapPOIItem[] markers = new MapPOIItem[InnerDBHelper.RtnCount()];
        for (int a = 0; a < InnerDBHelper.RtnCount(); a++) {
            markers[a] = new MapPOIItem();
        }

        for (int a = 0; a < InnerDBHelper.RtnCount(); a++) {
            markers[a].setItemName(data.get(a).getSightName());
            markers[a].setMapPoint(MapPoint.mapPointWithGeoCoord(data.get(a).getCoordinate_x(), data.get(a).getCoordinate_y()));
            markers[a].setMarkerType(MapPOIItem.MarkerType.BluePin);
            Log.d("LOG/Bucket", Double.toString(data.get(a).getCoordinate_x()));
            Log.d("LOG/Bucket", Double.toString(data.get(a).getCoordinate_y()));
            markers[a].setShowDisclosureButtonOnCalloutBalloon(false);
        }
        //////////////////////////////////////////////////////////////////////////////////
        mapView.addPOIItems(markers);

        addListViewItem();
        return layout;
    }

    public void loadData() {
        //디비호출 디비는 같은 폰에 하나가 아니라 앱에 하나임
        final InnerDBHelper InnerDBHelper = new InnerDBHelper(getActivity(), "BUCKETDB1.db", null, 1);
        data = new ArrayList<>();
        String[] placeName;
        placeName = InnerDBHelper.RtnPlaceName();
        SIGHT1000_LIST array;
        for (int a = 0; a < InnerDBHelper.RtnCount(); a++) {
            BucketGridViewItem item = new BucketGridViewItem();
            array = SIGHT1000ARRAY.getItemById(placeName[a]);
            item.setId(Integer.valueOf(array.getData(0)));
            item.setSightName(array.getData(1));
            item.setRecommend(Integer.parseInt(array.getData(3)));
            LoadBitmapfromUrl(array.getData(8), item);
            item.setCoordinate_x(Double.parseDouble(array.getData(4)));
            item.setCoordinate_y(Double.parseDouble(array.getData(5)));
            data.add(item);
        }
    }


    public void addListViewItem() {
        for (int i = 0; i < data.size(); i++) {
            adapter.addItem(data.get(i));
        }
    }

    // Uri -> 비트맵으로의 전환 메서드.
    public static void LoadBitmapfromUrl(final String uri, final BucketGridViewItem item) {
        class LoadClass extends AsyncTask<Object, Void, Bitmap> {
            @Override
            protected Bitmap doInBackground(Object... params) {
                String uri = (String) params[0];
                return loadBitmap(uri);
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                if (result != null) {
                    item.setBitmap(result);
                    refresh();
                }
            }

            public Bitmap loadBitmap(String uri) {
                if (synk == 1) {
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
                    Log.d("LOG/Bucket", "Get Bucket Bitmap! " + uri);

                    return bitmap;
                } else
                    return null;
            }
        }
        LoadClass inner = new LoadClass();
        inner.execute(uri);
    }

    public static void refresh() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        synk = 0;
    }

    @Override
    public void onPause() {
        super.onPause();
        BucketMapViewContainer.removeAllViews();
    }
}