package com.inu.h4.seoultriphelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class InnerDBHelper  extends SQLiteOpenHelper {

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public InnerDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 MONEYBOOK이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE BUCKETDB1 (_id INTEGER PRIMARY KEY AUTOINCREMENT, placeName TEXT );");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String getplaceName) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        //중복체크
        Cursor cursor = db.rawQuery("SELECT * FROM BUCKETDB1 where placeName = " + getplaceName , null);
        if(cursor.getCount() <=0 ){
            // DB에 입력한 값으로 행 추가
            db.execSQL("INSERT INTO BUCKETDB1 VALUES(null, '" + getplaceName + "');");
            db.close();
        }
    }

    public void update(String getplaceName) {

    }

    public void delete(String getplaceName) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM BUCKETDB1 WHERE placeName='" + getplaceName + "';");
        db.close();
    }

    public int RtnCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM BUCKETDB1", null);
        return cursor.getCount();
    }

    public String[] RtnPlaceName(){
        SQLiteDatabase db = getReadableDatabase();
        String[] result = new String[RtnCount()];
        int tmp = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM BUCKETDB1", null);
        while (cursor.moveToNext()){
            result[tmp] = cursor.getString(1) ;
            Log.d("LOG/RtnPlaceName",result[tmp]);
            tmp++;
        }
        return result;
    }


}