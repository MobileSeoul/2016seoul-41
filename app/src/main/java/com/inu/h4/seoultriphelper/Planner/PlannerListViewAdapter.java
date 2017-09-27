package com.inu.h4.seoultriphelper.Planner;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.inu.h4.seoultriphelper.R;

import java.util.ArrayList;


public class PlannerListViewAdapter extends BaseAdapter {
    PlannerListViewAdapter adapter = this;
    Fragment fragment;
    PlannerListEditDialog EditDialog;
    PlannerDB plannerDB;
    public PlannerListViewAdapter(Fragment fragment, PlannerDB plannerDB){
        this.fragment = fragment;
        this.plannerDB = plannerDB;
    }

    private ArrayList<PlannerListViewItem> listViewItemlist = new ArrayList<>();

    @Override
    public int getCount() {
        return listViewItemlist.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.planner_submit_layout, parent, false);
        }

        final PlannerListViewItem listViewItem = listViewItemlist.get(position);

        TextView name = (TextView) convertView.findViewById(R.id.SightName);
        TextView startTime = (TextView) convertView.findViewById(R.id.StartTime);
        TextView endTime = (TextView) convertView.findViewById(R.id.EndTime);
        TextView cost = (TextView) convertView.findViewById(R.id.Cost);
        TextView memo = (TextView) convertView.findViewById(R.id.Memo);
        TextView day = (TextView) convertView.findViewById(R.id.day);
        ImageView image = (ImageView) convertView.findViewById(R.id.Sightimage);

        name.setText(listViewItem.getP_name());
        startTime.setText(listViewItem.getP_t_sh() + "시 " + listViewItem.getP_t_sm() + "분");
        endTime.setText(listViewItem.getP_t_eh() + "시 " + listViewItem.getP_t_em() + "분");
        cost.setText(listViewItem.getP_cost() + "원");
        memo.setText(listViewItem.getP_memo());
        day.setText(listViewItem.getP_day() + "일");
        image.setImageBitmap(listViewItem.getImageBitmap());

        Button editButton = (Button) convertView.findViewById(R.id.edit_plan);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialog = new PlannerListEditDialog(context,
                        listViewItem,
                        plannerDB,
                        adapter);
                EditDialog.show();
            }
        });

        Button deleteButton = (Button) convertView.findViewById(R.id.delete_plan);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plannerDB.DBDelete(listViewItem.getP_name());
                listViewItemlist.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public void addItem(PlannerListViewItem item){
        listViewItemlist.add(item);
    }
}
