package com.inu.h4.seoultriphelper.Planner;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inu.h4.seoultriphelper.R;


public class PlannerListEditDialog extends Dialog {
    PlannerDB plannerDB;
    String name;
    int i_t_sh;
    int i_t_sm;
    int i_t_eh;
    int i_t_em;
    int i_cost;
    String s_memo;
    int i_day;
    PlannerListViewAdapter adapter;
    PlannerListViewItem item;
    public PlannerListEditDialog(Context context, PlannerListViewItem item, PlannerDB plannerDB, PlannerListViewAdapter adapter){
        super(context);
        this.item = item;
        this.name = item.getP_name();
        i_t_sh = item.getP_t_sh();
        i_t_sm = item.getP_t_sm();
        i_t_eh = item.getP_t_eh();
        i_t_em = item.getP_t_em();
        i_cost = item.getP_cost();
        s_memo = item.getP_memo();
        i_day = item.getP_day();
        this.plannerDB = plannerDB;
        this.adapter = adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.7f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.planner_edit_dialog);

        final EditText t_sh = (EditText) findViewById(R.id.inputhour_S);
        final EditText t_sm = (EditText) findViewById(R.id.inputminute_S);
        final EditText t_eh = (EditText) findViewById(R.id.inputhour_E);
        final EditText t_em = (EditText) findViewById(R.id.inputminute_E);
        final EditText cost = (EditText) findViewById(R.id.Cost);
        final EditText memo = (EditText) findViewById(R.id.Memo);
        final EditText day = (EditText) findViewById(R.id.Day_E);
//
//        t_sh.setText(Integer.toString(this.t_sh));
//        t_sm.setText(Integer.toString(this.t_sm));
//        t_eh.setText(Integer.toString(this.t_eh));
//        t_em.setText(Integer.toString(this.t_em));
//        cost.setText(Integer.toString(this.cost));
//        memo.setText(this.t_sh);

        Button editButton = (Button) findViewById(R.id.planner_edit_btn);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_t_sh = t_sh.getText().toString();
                String s_t_sm = t_sm.getText().toString();
                String s_t_eh = t_eh.getText().toString();
                String s_t_em = t_em.getText().toString();
                String s_cost = cost.getText().toString();
                String s_day = day.getText().toString();
                if(s_t_sh.equals(""))
                    i_t_sh = 0;
                else
                    i_t_sh = Integer.parseInt(s_t_sh);
                if(s_t_sm.equals(""))
                    i_t_sm = 0;
                else
                    i_t_sm = Integer.parseInt(s_t_sm);
                if(s_t_eh.equals(""))
                    i_t_eh = 0;
                else
                    i_t_eh = Integer.parseInt(s_t_eh);
                if(s_t_em.equals(""))
                    i_t_em = 0;
                else
                    i_t_em = Integer.parseInt(s_t_em);
                if(s_cost.equals(""))
                    i_cost = 0;
                else
                    i_cost = Integer.parseInt(s_cost);
                if(s_day.equals(""))
                    i_day = 0;
                else
                    i_day = Integer.parseInt(s_day);

                s_memo = memo.getText().toString();

                plannerDB.DBEdit(name, i_t_sh, i_t_sm, i_t_eh, i_t_em, i_cost, s_memo, i_day);
                item.setP_t_sh(i_t_sh);
                item.setP_t_sm(i_t_sm);
                item.setP_t_eh(i_t_eh);
                item.setP_t_em(i_t_em);
                item.setP_cost(i_cost);
                item.setP_memo(s_memo);
                item.setP_day(i_day);

                adapter.notifyDataSetChanged();

                Toast.makeText(getContext(), "수정 완료", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        Button cancelButton = (Button) findViewById(R.id.planner_cancel_btn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
