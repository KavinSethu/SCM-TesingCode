package com.pnf.elar.app.adapter.schedule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnf.elar.app.Bo.schedule.ScheduleMonthList;
import com.pnf.elar.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VKrishnasamy on 22-12-2016.
 */

public class SchemaAdapter extends RecyclerView.Adapter<SchemaAdapter.ActivityViewHolder> {

    View itemView;
    LayoutInflater layoutInflater;
    List<ScheduleMonthList.SchemaCalendarEntity> activityList=new ArrayList<>();
    Context ctx;
    String language;


    public SchemaAdapter(Context context,  List<ScheduleMonthList.SchemaCalendarEntity> activityList, String lang) {
        this.ctx=context;
        this.activityList=activityList;
        this.layoutInflater=LayoutInflater.from(ctx);
        this.language=lang;


    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView=layoutInflater.inflate(R.layout.item_activity,parent,false);
        return new ActivityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {

        holder.activityNameText.setText(activityList.get(position).getSchema().getTitle());
        if(language.equalsIgnoreCase("en")) {
            holder.tv_startDateText.setText("Start : " + activityList.get(position).getSchema().getStart());
            holder.tv_endDateText.setText("End   : " + activityList.get(position).getSchema().getEnd());
        }else{
            holder.tv_startDateText.setText("Start : " + activityList.get(position).getSchema().getStart());
            holder.tv_endDateText.setText("Slutet   : " + activityList.get(position).getSchema().getEnd());
        }

    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {

        TextView activityNameText;
        TextView tv_startDateText;
        TextView tv_endDateText;

        ActivityViewHolder(View itemView) {
            super(itemView);
            activityNameText = (TextView) itemView.findViewById(R.id.activityNameText);
            tv_startDateText = (TextView) itemView.findViewById(R.id.tv_startDateText);
            tv_endDateText = (TextView) itemView.findViewById(R.id.tv_endDateText);
        }


    }
}
