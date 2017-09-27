package com.inu.h4.seoultriphelper.DataBase;

import android.os.AsyncTask;
import android.util.Log;

import com.inu.h4.seoultriphelper.Detail.SightDetailFragment;
import com.inu.h4.seoultriphelper.Detail.SightDetailItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SIGHT1000ForDetailSight {

    private static final String SIGHT_NAME = "SIGHT_NAME";
    private static final String SIGHT_INFO = "SIGHT_INFO";
    private static final String SIGHT_RECOMMEND_COUNT = "SIGHT_RECOMMEND_COUNT";
    private static final String SIGHT_LOCATION_X = "SIGHT_LOCATION_X";
    private static final String SIGHT_LOCATION_Y = "SIGHT_LOCATION_Y";

    public static void getData(String sight_id, final SightDetailItem item) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params){
                try{
                    String sight_id = params[0];

                    String link = "http://52.42.208.72/Sight1000GetDataForDetail.php";
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

                    String sightName = obj.getString(SIGHT_NAME);
                    String sightInfo = obj.getString(SIGHT_INFO);
                    String sightRecommendCount = obj.getString(SIGHT_RECOMMEND_COUNT);
                    String sightLocationX = obj.getString(SIGHT_LOCATION_X);
                    String sightLocationY = obj.getString(SIGHT_LOCATION_Y);

                    item.setSightName(sightName);
                    item.setSightInfo(sightInfo);
                    item.setRecommendCount(Integer.valueOf(sightRecommendCount));
                    item.setSightX(Double.valueOf(sightLocationX));
                    item.setSightY(Double.valueOf(sightLocationY));

                    Log.d("LOG/DB","Get Item");

                    SightDetailFragment.notifySightData();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(sight_id);
    }
    public static void incrementDetailRecommendCount(String sight_id, final SightDetailItem item) {
        class incrementDetailRecommendCountClass extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params){
                try{
                    String sight_id = params[0];

                    String link = "http://52.42.208.72/Sight1000UpdateRecommend.php";
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

                    String sightRecommendCount = obj.getString(SIGHT_RECOMMEND_COUNT);

                    item.setRecommendCount(Integer.valueOf(sightRecommendCount));

                    SightDetailFragment.notifyRecommendCount();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
        incrementDetailRecommendCountClass incrementDetailRecommendCountClass = new incrementDetailRecommendCountClass();
        incrementDetailRecommendCountClass.execute(sight_id);
    }
    public static void addSumPointAndPeopleCount(String sight_id, String point) {
        class incrementHomeRecommendCountClass extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params){
                try{
                    String sight_id = params[0];
                    String point = params[1];

                    String link = "http://52.42.208.72/Review1000UpdatePoint.php";
                    String data = URLEncoder.encode("SIGHT_ID", "UTF-8") + "=" + URLEncoder.encode(sight_id, "UTF-8");
                    data += "&" + URLEncoder.encode("SUM_POINT", "UTF-8") + "=" + URLEncoder.encode(point, "UTF-8");

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
        }
        incrementHomeRecommendCountClass incrementRecommendCountClass = new incrementHomeRecommendCountClass();
        incrementRecommendCountClass.execute(sight_id, point);
    }
}
