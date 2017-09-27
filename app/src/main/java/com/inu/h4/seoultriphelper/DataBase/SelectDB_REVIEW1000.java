package com.inu.h4.seoultriphelper.DataBase;

import android.os.AsyncTask;
import android.util.Log;

import com.inu.h4.seoultriphelper.Detail.SightDetailFragment;
import com.inu.h4.seoultriphelper.Detail.SightDetailItem;
import com.inu.h4.seoultriphelper.Detail.SightDetailReviewListViewItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class SelectDB_REVIEW1000 {

    static String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_REVIEW_WRITER = "review_writer";
    private static final String TAG_REVIEW_INFO = "review_info";
    private static final String TAG_REVIEW_DATE = "review_date";
    private static final String TAG_REVIEW_POINT = "review_point";

    protected static void showList(List<SightDetailReviewListViewItem> reviewList){
        try{
            JSONObject jsonObj = new JSONObject(myJSON);
            JSONArray sights = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0; i<sights.length(); i++){
                JSONObject c = sights.getJSONObject(i);
                String reviewWriter = c.getString(TAG_REVIEW_WRITER);
                String reviewInfo = c.getString(TAG_REVIEW_INFO);
                String reviewDate = c.getString(TAG_REVIEW_DATE);
                String reviewPoint = c.getString(TAG_REVIEW_POINT);

                SightDetailReviewListViewItem item = new SightDetailReviewListViewItem();
                item.setWriter(reviewWriter);
                item.setInfo(reviewInfo);
                item.setDate(reviewDate);
                item.setRecommendRating(Float.valueOf(reviewPoint));

                Log.d("LOG/DB","Get Item");
                reviewList.add(item);
            }

            SightDetailFragment.notifyReviewData();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static void getData(String sight_id, final List<SightDetailReviewListViewItem> reviewList) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params){
                try{
                    String sight_id = params[0];

                    String link = "http://52.42.208.72/Review1000GetData.php";
                    String data = URLEncoder.encode("SIGHT_ID", "UTF-8") + "=" + URLEncoder.encode(sight_id, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while((line = reader.readLine()) != null){
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                }
                catch (Exception e){
                    return new String("Exception: " + e.getMessage());
                }
            }

            @Override
            protected void onPostExecute(String result){
                myJSON = result;
                showList(reviewList);
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(sight_id);
    }
    public static void setAvgRecommendPoint(String sight_id, final SightDetailItem item) {
        class GetAvg extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params){
                try{
                    String sight_id = params[0];

                    String link = "http://52.42.208.72/Review1000GetAvg.php";
                    String data = URLEncoder.encode("SIGHT_ID", "UTF-8") + "=" + URLEncoder.encode(sight_id, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();


                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while((line = reader.readLine()) != null){
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                }
                catch (Exception e){
                    return new String("Exception: " + e.getMessage());
                }
            }

            @Override
            protected void onPostExecute(String result){
                try{
                    JSONObject obj = new JSONObject(result);

                    String avgPoint = obj.getString("avg(REVIEW_POINT)");
                    Log.d("LOG/REVIEW1000", avgPoint);

                    if(!avgPoint.contains("null")) {
                        double dAvgPoint = Math.round(Double.valueOf(avgPoint)*10d) / 10d;
                        item.setAvgPoint(dAvgPoint);
                    }
                    else
                        item.setAvgPoint(0.0);

                    SightDetailFragment.notifyAvgPoint();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
        GetAvg avg = new GetAvg();
        avg.execute(sight_id);
    }
}
