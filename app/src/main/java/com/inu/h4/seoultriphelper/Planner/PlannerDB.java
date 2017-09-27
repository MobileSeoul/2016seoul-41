package com.inu.h4.seoultriphelper.Planner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class PlannerDB extends SQLiteOpenHelper{

    SQLiteDatabase sqLiteDatabase;

    public PlannerDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql  ="CREATE TABLE PLANNER(P_NAME TEXT PRIMARY KEY, " +
                "P_TIME_SH INTEGER, " +
                "P_TIME_SM INTEGER, " +
                "P_TIME_EH INTEGER, " +
                "P_TIME_EM INTEGER, " +
                "P_COST INTEGER, " +
                "P_MEMO TEXT, " +
                "P_DAY INTEGER);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE PLANNER;";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public Boolean DBInsert(String name, int t_sh, int t_sm, int t_eh, int t_em, int cost, String memo, int day){
        sqLiteDatabase = getWritableDatabase();
        if(IsOverLabed(name) == false){
            Log.d("오버랩안됨", "굿");
            String sql = "INSERT INTO PLANNER VALUES('"
                    + name + "', "
                    + t_sh + ", "
                    + t_sm + ", "
                    + t_eh + ", "
                    + t_em + ", "
                    + cost + ", '"
                    + memo + "', "
                    + day + ");";
            sqLiteDatabase.execSQL(sql);
            sqLiteDatabase.close();
            return true;
        }
        return false;
    }

    public Boolean IsOverLabed(String name){
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("PLANNER", null,  null, null, null, null, null);
        while(cursor.moveToNext()){
            Log.d("디비 = ", cursor.getString(cursor.getColumnIndex("P_NAME")));
            if(name.equals(cursor.getString(cursor.getColumnIndex("P_NAME")))){
                return true;
            }
        }
        return false;
    }

    public void DBSelect(ArrayList<PlannerListViewItem> data, PlannerListViewAdapter adapter){
        sqLiteDatabase = getReadableDatabase();
        data = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("PLANNER", null,  null, null, null, null, "P_DAY ASC");
        PlannerListViewItem item;
        int i = 0;
        while(cursor.moveToNext()){
            item = new PlannerListViewItem();
            item.setP_name(cursor.getString(cursor.getColumnIndex("P_NAME")));
            item.setP_t_sh(Integer.parseInt(cursor.getString(cursor.getColumnIndex("P_TIME_SH"))));
            item.setP_t_sm(Integer.parseInt(cursor.getString(cursor.getColumnIndex("P_TIME_SM"))));
            item.setP_t_eh(Integer.parseInt(cursor.getString(cursor.getColumnIndex("P_TIME_EH"))));
            item.setP_t_em(Integer.parseInt(cursor.getString(cursor.getColumnIndex("P_TIME_EM"))));
            item.setP_cost(Integer.parseInt(cursor.getString(cursor.getColumnIndex("P_COST"))));
            item.setP_memo(cursor.getString(cursor.getColumnIndex("P_MEMO")));
            item.setP_day(Integer.parseInt(cursor.getString(cursor.getColumnIndex("P_DAY"))));
            Log.d("디비 = ", cursor.getString(cursor.getColumnIndex("P_NAME")));
            data.add(item);
            adapter.addItem(data.get(i++));
        }
    }

    public void DBDelete(String name){
        sqLiteDatabase = getWritableDatabase();
        String sql = "DELETE FROM PLANNER WHERE P_NAME='" + name + "';";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    public void DBEdit(String name, int t_sh, int t_sm, int t_eh, int t_em, int cost, String memo, int day){
        String s_t_sh = Integer.toString(t_sh);
        String s_t_sm = Integer.toString(t_sm);
        String s_t_eh = Integer.toString(t_eh);
        String s_t_em = Integer.toString(t_em);
        String s_cost = Integer.toString(cost);
        String s_day = Integer.toString(day);

        sqLiteDatabase = getWritableDatabase();
        String sql = "UPDATE PLANNER SET P_TIME_SH = '" + s_t_sh
                + "', P_TIME_SM = '" + s_t_sm
                + "', P_TIME_EH = '" + s_t_eh
                + "', P_TIME_EM = '" + s_t_em
                + "', P_COST = '" + s_cost
                + "', P_MEMO = '" + memo
                + "', P_DAY = '" + day
                + "' WHERE P_NAME = '" + name + "';"
                ;
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    public int RtnCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PLANNER", null);
        Log.d("LOG/PLANNER_DB", String.valueOf(cursor.getCount()));
        return cursor.getCount();
    }
}