package com.inu.h4.seoultriphelper.DataBase;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class InsertDB_REVIEW1000 {
    public static void InsertToDatabase(String review_writers, String review_infos, String review_points, String sight_ids){
        class InsertData extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params){
                try{
                    String review_writer = params[0];
                    String review_info = params[1];
                    String review_point = params[2];
                    String sight_id = params[3];

                    String link = "http://52.42.208.72/Review1000SetData.php";
                    String data = URLEncoder.encode("REVIEW_WRITER", "UTF-8") + "=" + URLEncoder.encode(review_writer, "UTF-8");
                    data += "&" + URLEncoder.encode("REVIEW_INFO", "UTF-8") + "=" + URLEncoder.encode(review_info, "UTF-8");
                    data += "&" + URLEncoder.encode("REVIEW_POINT", "UTF-8") + "=" + URLEncoder.encode(review_point, "UTF-8");
                    data += "&" + URLEncoder.encode("SIGHT_ID", "UTF-8") + "=" +URLEncoder.encode(sight_id, "UTF-8");

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

        InsertData task = new InsertData();
        task.execute(review_writers, review_infos, review_points, sight_ids);
    }
}