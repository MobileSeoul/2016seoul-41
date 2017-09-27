package com.inu.h4.seoultriphelper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.inu.h4.seoultriphelper.Bucket.BucketEmptyFragment;
import com.inu.h4.seoultriphelper.Bucket.BucketExistFragment;
import com.inu.h4.seoultriphelper.DataBase.SIGHT1000ARRAY;
import com.inu.h4.seoultriphelper.DataBase.SIGHT1000_LIST;
import com.inu.h4.seoultriphelper.DataBase.SIGHT1100ForDetailImage;
import com.inu.h4.seoultriphelper.DataBase.TAG1000ARRAY;
import com.inu.h4.seoultriphelper.DataBase.TAG1000_LIST;
import com.inu.h4.seoultriphelper.DataBase.TAG1100ARRAY;
import com.inu.h4.seoultriphelper.DataBase.TAG1100_LIST;
import com.inu.h4.seoultriphelper.DataBase.TAG1200ARRAY;
import com.inu.h4.seoultriphelper.DataBase.TAG1200_LIST;
import com.inu.h4.seoultriphelper.Home.HomeFragment;
import com.inu.h4.seoultriphelper.Planner.PlannerDB;
import com.inu.h4.seoultriphelper.Planner.PlannerEmptyFragment;
import com.inu.h4.seoultriphelper.Planner.PlannerExistFragment;
import com.inu.h4.seoultriphelper.Prefer.PreferEmptyFragment;
import com.inu.h4.seoultriphelper.Prefer.PreferExistFragment;
import com.inu.h4.seoultriphelper.Search.SearchListViewAdapter;
import com.inu.h4.seoultriphelper.Search.SearchListViewItem;
import com.inu.h4.seoultriphelper.Setting.SettingActivity;
import com.inu.h4.seoultriphelper.Tag.TagMainFragment;
import com.inu.h4.seoultriphelper.Tag.TagSelectedSightFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    phpDown task1, task2, task3, task4;

    SIGHT1000_LIST sight1000list;
    TAG1000_LIST tag1000list;
    TAG1100_LIST tag1100list;
    TAG1200_LIST tag1200list;

    TAG1000ARRAY tag1000array;
    TAG1100ARRAY tag1100array;
    TAG1200ARRAY tag1200array;

    static Bitmap bmImg;

    private DrawerLayout drawer;
    private Fragment initFragment;
    private FragmentTransaction transaction;

    private BackPressCloseSystem backPressCloseSystem;
    static int InnerDbCounter=0;
    final InnerDBHelper InnerDBHelper1 = new InnerDBHelper(this, "BUCKETDB1.db",null,1);
    InnerDBHelper2 innerDBHelper2 = new InnerDBHelper2(this, "PREFERDB2.db",null,1);
    PlannerDB plannerDB;

    // 검색 관련 변수들
    private SearchListViewAdapter adapter;
    private SearchListViewItem searchItem;
    private ListView searchListview;

    @Override
    public void onBackPressed() {
        Log.d("LOG/MAIN", "onBackPressed()");

        // DB 데이터를 불러오는 중에 뒤로가기를 눌렀을 경우.
        if(SIGHT1100ForDetailImage.synk != SIGHT1100ForDetailImage.STOP) {
            Log.d("LOG/MAIN", "SIGHT1100 synk = STOP");
            SIGHT1100ForDetailImage.synk = SIGHT1100ForDetailImage.STOP;
        }

        // 좌측의 drawer 메뉴가 켜져있는 경우 뒤로가기 버튼을 누르면 닫음
        if (this.drawer.isDrawerOpen(GravityCompat.START)) {
            this.drawer.closeDrawer(GravityCompat.START);
        } else {
            backPressCloseSystem.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("LOG/MAIN", "onCreate()");

        plannerDB = new PlannerDB(this, "planner.db", null, 1);

        tag1000array = TAG1000ARRAY.getInstance();
        tag1100array = TAG1100ARRAY.getInstance();
        tag1200array = TAG1200ARRAY.getInstance();

        task1 = new phpDown("sight1000");
        task1.execute("http://52.42.208.72/Sight1000GetData.php");

        task2 = new phpDown("tag1000");
        task2.execute("http://52.42.208.72/Tag1000GetData.php");

        task3 = new phpDown("tag1100");
        task3.execute("http://52.42.208.72/Tag1100GetData.php");

        task4 = new phpDown("tag1200");
        task4.execute("http://52.42.208.72/Tag1200GetData.php");

        // 앱 최상단에 메뉴, 검색버튼과 화면 이름을 출력하는 툴바를 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 검색을 위한 멤버들 초기화
        adapter = new SearchListViewAdapter(this);
        searchListview = (ListView) findViewById(R.id.search_listview);

        // 뒤로가기 버튼 이벤트 등록
        backPressCloseSystem = new BackPressCloseSystem(this);

        // 좌측 메뉴를 생성하는 drawer layout
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private class back extends AsyncTask<String, Integer, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            try{
                URL myFileUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                //String json = DownloadHtml("http://117.16.243.116/appdata.php");
                InputStream is = conn.getInputStream();

                bmImg = BitmapFactory.decodeStream(is);

            }catch(IOException e){
                e.printStackTrace();
            }
            return bmImg;
        }
    }

    private class phpDown extends AsyncTask<String, Integer, String>{

        private int select;

        phpDown(String command) {
            if(command.equals("sight1000")) {
                select = 0;
            } else if (command.equals("tag1000")) {
                select = 1;
            } else if (command.equals("tag1100")) {
                select = 2;
            } else if (command.equals("tag1200")) {
                select = 3;
            }
        }

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            try{
                // 연결 url 설정
                URL url = new URL(urls[0]);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                // 연결되었으면.
                if(conn != null){
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    // 연결되었음 코드가 리턴되면.
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for(;;){
                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br.readLine();
                            if(line == null) break;
                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch(Exception ex){
                ex.printStackTrace();
            }
            return jsonHtml.toString();
        }

        protected void onPostExecute(String str){
            if(select == 0) {
                loadSight1000(str);
            } else if (select == 1) {
                loadTag1000(str);
            } else if (select == 2) {
                loadTag1100(str);
            } else if (select == 3) {
                loadTag1200(str);
            }
        }
        private void loadSight1000(String str) {
            try{
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("result");
                for(int i=0; i<ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);
                    String id = jo.getString("sight_id");
                    String name = jo.getString("sight_name");
                    String info = jo.getString("sight_info");
                    String recommend_count = jo.getString("sight_recommend_count");
                    String location_x = jo.getString("sight_location_x");
                    String location_y = jo.getString("sight_location_y");
                    String weekrecommend = jo.getString("sight_weekrecommend");
                    String monthrecommend = jo.getString("sight_monthrecommend");
                    String thumbnail = jo.getString("sight_thumbnail");
                    String sumpoint = jo.getString("sum_point");
                    String peoplecount = jo.getString("p_count");
                    String category = jo.getString("category");

                    sight1000list = new SIGHT1000_LIST();
                    sight1000list.setSight1000Data(
                            id,
                            name,
                            info,
                            recommend_count,
                            location_x,
                            location_y,
                            weekrecommend,
                            monthrecommend,
                            thumbnail,
                            sumpoint,
                            peoplecount,
                            category);

                    SIGHT1000ARRAY.sight1000Array.add(sight1000list);

                    // 검색을 위한 데이터 삽입
                    searchItem = new SearchListViewItem();
                    searchItem.setName(name);
                    searchItem.setPlaceId(Integer.parseInt(id));

                    adapter.addItem(searchItem);
                }

                // 각 페이지에 해당하는 Fragment 초기화
                initFragment = new HomeFragment();
                // 초기 화면으로 사용할 fragment 설정
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.container, initFragment, "page_home");
                transaction.addToBackStack("page_home");
                transaction.commit();


                searchListview.setAdapter(adapter);
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }

        private void loadTag1000(String str) {
            try{
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("result");
                for(int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    String tag_group_id = jo.getString("tag_group_id");
                    String tag_group_name = jo.getString("tag_group_name");
                    Log.d("LOG/MAIN", "tag1000 load : " + tag_group_name);

                    tag1000list = new TAG1000_LIST();
                    tag1000list.setTag1000Data(
                            tag_group_id,
                            tag_group_name);

                    tag1000array.addData(tag1000list);
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }

        private void loadTag1100(String str) {
            try{
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("result");
                for(int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    String tag_id = jo.getString("tag_id");
                    String tag_name = jo.getString("tag_name");
                    String tag_group_id = jo.getString("tag_group_id");
                    Log.d("LOG/MAIN", "tag1100 load : " + tag_id);

                    tag1100list = new TAG1100_LIST();
                    tag1100list.setTag1100Data(
                            tag_id,
                            tag_name,
                            tag_group_id);

                    tag1100array.addData(tag1100list);
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }

        private void loadTag1200(String str) {
            try{
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("result");
                for(int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    String sight_id = jo.getString("sight_id");
                    String tag_id = jo.getString("tag_id");
                    Log.d("LOG/MAIN", "tag1200 load : " + sight_id);
                    tag1200list = new TAG1200_LIST();
                    tag1200list.setTag1200Data(
                            sight_id,
                            tag_id);

                    tag1200array.addData(tag1200list);
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("LOG/MAIN", "onCreateOptionMenu()");
        getMenuInflater().inflate(R.menu.main, menu);

        // SearchView Hint 변경하기
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("여행지를 검색해 보세요.");

        adapter.setMenuItem(searchItem); // 검색 종료 후 창을 닫기 위해 객체를 보냄.

        // SearchView 입력 글자색과 힌트 색상 변경하기
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)searchView
                .findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.GRAY);
        searchAutoComplete.setTextColor(Color.WHITE);

        // SearchView 확장/축소 이벤트 처리
        MenuItemCompat.OnActionExpandListener expandListener = new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // 검색창이 팽창되면 숨어있던 레이아웃이 보이게 되며 다른 창은 클릭하지 못하게 됨.
//                findViewById(R.id.search_result).setVisibility(View.VISIBLE);
//                findViewById(R.id.search_result).bringToFront();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                findViewById(R.id.search_result).setVisibility(View.GONE);
                return true;
            }
        };

        MenuItemCompat.setOnActionExpandListener(searchItem, expandListener);

        // SearchView 검색어 입력/검색 이벤트 처리
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String input) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String input) {
                if(! input.equals("")) {
                    findViewById(R.id.search_result).setVisibility(View.VISIBLE);
                    findViewById(R.id.search_result).bringToFront();
                } else {
                    findViewById(R.id.search_result).setVisibility(View.GONE);
                }

                String text = input.toLowerCase(Locale.getDefault());
                adapter.filter(text);
                searchListview.setAdapter(adapter);

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("LOG/MAIN", "onOptionSelected()");
        switch(item.getItemId()) {
            case R.id.action_search:
                // TODO 돋보기 버튼 동작 삽입
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.d("LOG/MAIN", "onNavigationItemSelected()");

        int id = item.getItemId();

        String tag = "";
        String currentFragmentTag = "";

        Fragment targetFragment = null;
        transaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_home) {
            targetFragment = new HomeFragment();
            tag = "page_home";
        } else if (id == R.id.nav_tag) {
            //targetFragment = new TagMainFragment();
            //tag = "page_tag";
        } else if (id == R.id.nav_prefer) {
            if(innerDBHelper2.selectPrefer() == null) {          // 설문 결과가 없을 경우
                targetFragment = new PreferEmptyFragment();
                tag = "page_prefer_empty";
            } else {            // 설문 결과가 있을 경우
                targetFragment = new PreferExistFragment();
                tag = "page_prefer_exist";
            }
        } else if (id == R.id.nav_bucket) {
            if(InnerDBHelper1.RtnCount()==0) {          // 버킷리스트가 비어있을 경우
                targetFragment = new BucketEmptyFragment();
                tag = "page_bucket_empty";
            } else {            // 버킷리스트가 비어있지 않은 경우
                targetFragment = new BucketExistFragment();
                tag = "page_bucket_exist";
            }
        } else if (id == R.id.nav_planner) {
            if(plannerDB.RtnCount() == 0) {
                targetFragment = new PlannerEmptyFragment();
                tag = "page_planner_empty";
            } else {
                targetFragment = new PlannerExistFragment();
                tag = "page_planner_exist";
            }

        } else if (id == R.id.nav_map) {

        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }

        // 현재 띄워진 Fragment의 tag를 받아옴.
        currentFragmentTag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager()
                .getBackStackEntryCount() - 1).getName();

        // tag를 통해 Fragment를 구분하고 바뀔 페이지가 같은 것이면 아무 동작도 하지 않는다.
        if(tag.equals(currentFragmentTag)) {
            if (this.drawer.isDrawerOpen(GravityCompat.START)) {
                this.drawer.closeDrawer(GravityCompat.START);
            }
            Log.d("LOG/MAIN", "not replace");
            return true;
        }

        Log.d("LOG/MAIN", currentFragmentTag + " -> " + tag);

        if(targetFragment == null) {
            if (this.drawer.isDrawerOpen(GravityCompat.START)) {
                this.drawer.closeDrawer(GravityCompat.START);
            }
            return true;
        }

        transaction.replace(R.id.container, targetFragment, tag);
        transaction.addToBackStack(tag);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.commitAllowingStateLoss();
        //transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}