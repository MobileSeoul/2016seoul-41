package com.inu.h4.seoultriphelper.DataBase;

import android.os.AsyncTask;
import android.util.Log;

import com.inu.h4.seoultriphelper.Prefer.PreferExistFragment;

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

public class TAGForPrefer {
    public static void getTagId(final String[] tagIdList, final List<String> sightIdList) {
        class GetTagIdClass extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params){
                try{
                    String[] tagList = tagIdList;

                    String link = "http://52.42.208.72/TagForPrefer.php";
                    String data = URLEncoder.encode("TAG_1", "UTF-8") + "=" + URLEncoder.encode(tagList[0], "UTF-8");
                    data += "&" + URLEncoder.encode("TAG_2", "UTF-8") + "=" + URLEncoder.encode(tagList[1], "UTF-8");
                    data += "&" + URLEncoder.encode("TAG_3", "UTF-8") + "=" + URLEncoder.encode(tagList[2], "UTF-8");

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
                    JSONObject root = new JSONObject(result);
                    JSONArray ja = root.getJSONArray("result");

                    for(int i=0;i<ja.length();i++) {
                        JSONObject obj = ja.getJSONObject(i);
                        String sight_id = obj.getString("sight_id");

                        sightIdList.add(sight_id);
                        Log.d("LOG/TAG_DB", sight_id);
                    }
                    PreferExistFragment.synk = 1;

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
        GetTagIdClass getTagIdClass = new GetTagIdClass();
        getTagIdClass.execute();
    }
}
