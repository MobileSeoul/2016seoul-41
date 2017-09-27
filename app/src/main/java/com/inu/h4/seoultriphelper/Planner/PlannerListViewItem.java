package com.inu.h4.seoultriphelper.Planner;


import android.graphics.Bitmap;

public class PlannerListViewItem {
    private String P_name;
    private int P_t_sh;
    private int P_t_sm;
    private int P_t_eh;
    private int P_t_em;
    private int P_cost;
    private String P_memo;
    private int P_day;
    private Bitmap imageBitmap;

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }
    public String getP_name(){
        return P_name;
    }
    public int getP_t_sh(){
        return P_t_sh;
    }
    public int getP_t_sm(){
        return P_t_sm;
    }
    public int getP_t_eh(){
        return P_t_eh;
    }
    public int getP_t_em(){
        return P_t_em;
    }
    public int getP_cost(){
        return P_cost;
    }
    public String getP_memo(){
        return P_memo;
    }
    public int getP_day(){
        return P_day;
    }

    public void setP_name(String P_name){this.P_name = P_name;}
    public void setP_t_sh(int P_t_sh){this.P_t_sh = P_t_sh;}
    public void setP_t_sm(int P_t_sm){this.P_t_sm = P_t_sm;}
    public void setP_t_eh(int P_t_eh){this.P_t_eh = P_t_eh;}
    public void setP_t_em(int P_t_em){this.P_t_em = P_t_em;}
    public void setP_cost(int P_cost){this.P_cost = P_cost;}
    public void setP_memo(String P_memo){this.P_memo = P_memo;}
    public void setP_day(int P_day){this.P_day = P_day;}
    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
}
