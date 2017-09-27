package com.inu.h4.seoultriphelper.DataBase;

import android.os.AsyncTask;
import android.util.Log;

import com.inu.h4.seoultriphelper.Detail.SightDetailFragment;

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

public class SIGHT1100ForDetailImage {

    static String myJSON;

    private static final String SERVER_IP = "http://52.42.208.72/";
    private static final String TAG_RESULTS = "result";
    private static final String IMAGE_FILEPATH = "SIGHT_IMAGE_FILEPATH";
    private static final String IMAGE_FILENAME = "SIGHT_IMAGE_FILENAME";
    public static final int STOP = 0;       // DB 연동 시작 전 상태.
    public static final int START = 1;      // DB 연동 시작 상태.
    public static final int MIDTERM = 2;    // DB 연동 중간지점.
    public static int synk;     // DB 연동 싱크를 맞춰 줄 변수.

    public static void getData(String sight_id, final List<String> imageUris) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params){
                try{
                    String sight_id = params[0];

                    String link = "http://52.42.208.72/Sight1100GetDataForDetail.php";
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
                    JSONObject root = new JSONObject(result);
                    JSONArray ja = root.getJSONArray(TAG_RESULTS);

                    for(int i=0;i<ja.length();i++) {
                        if(synk == SIGHT1100ForDetailImage.START) {
                            JSONObject obj = ja.getJSONObject(i);
                            String sightImageFilePath = obj.getString(IMAGE_FILEPATH);
                            String sightImageFileName = obj.getString(IMAGE_FILENAME);

                            String fullPath = SERVER_IP.concat(sightImageFilePath).concat(sightImageFileName);

                            imageUris.add(fullPath);

                            Log.d("LOG/DB", "Get Path");
                        } else {
                            Log.d("LOG/DB", "Uri Clear");
                            imageUris.clear();
                            break;
                        }
                    }
                    if(synk == SIGHT1100ForDetailImage.START) {
                        synk = SIGHT1100ForDetailImage.MIDTERM;
                        SightDetailFragment.LoadBitmapfromUrl(imageUris);
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(sight_id);
    }
}
