package com.inu.h4.seoultriphelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class InnerDBHelper2  extends SQLiteOpenHelper {

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public InnerDBHelper2(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 MONEYBOOK이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE PREFERDB2 (_id INTEGER PRIMARY KEY AUTOINCREMENT, _index TEXT );");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertPrefer(String index) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        //중복체크
        Cursor cursor = db.rawQuery("SELECT * FROM PREFERDB2 where _index = '" + index + "';" , null);
        if(cursor.getCount() <=0 ){
            // DB에 입력한 값으로 행 추가
            db.execSQL("INSERT INTO PREFERDB2(_index) VALUES('" + index + "');");
            db.close();
        }
    }
    public void deletePrefer(String index) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM PREFERDB2 WHERE _index='" + index + "';");
        db.close();
    }
    public String selectPrefer(){
        SQLiteDatabase db = getReadableDatabase();
        String result = null;
        Cursor cursor = db.rawQuery("SELECT * FROM PREFERDB2", null);
        while (cursor.moveToNext()){
            result = cursor.getString(1) ;
            Log.d("LOG/RtnPlaceName",result);
        }
        return result;
    }


}