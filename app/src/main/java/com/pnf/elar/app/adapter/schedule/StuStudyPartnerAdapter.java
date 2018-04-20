package com.pnf.elar.app.adapter.schedule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.pnf.elar.app.Bo.schedule.StudentsUserEntity;
import com.pnf.elar.app.R;
import com.pnf.elar.app.util.AppLog;

import java.util.ArrayList;
import java.util.List;



public class StuStudyPartnerAdapter extends RecyclerView.Adapter<StuStudyPartnerAdapter.ViewHolder> {

    Context context;
    List<StudentsUserEntity> stuStudyPartnerList=new ArrayList<>();

    public StuStudyPartnerAdapter(Context context, List<StudentsUserEntity> stuStudyPartnerList)
    {
        super();
        this.context=context;
        this.stuStudyPartnerList=stuStudyPartnerList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_spinner,parent,false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.nameText.setText(stuStudyPartnerList.get(position).getName());
        holder.idText.setText(stuStudyPartnerList.get(position).getId());
        holder.checkBox.setChecked(stuStudyPartnerList.get(position).isSelected());
        holder.checkBox.setVisibility(View.VISIBLE);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                AppLog.Log("bool ",b+"");
               /* if(b)
                {*/


                stuStudyPartnerList.get(position).setSelected(b);
/*
                }
*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return stuStudyPartnerList.size();
    }


    public class  ViewHolder extends   RecyclerView.ViewHolder

    {
        TextView nameText;
        TextView idText;
        CheckBox checkBox;


        public ViewHolder(View itemView) {
            super(itemView);

            nameText = (TextView) itemView.findViewById(R.id.nameText);
            idText = (TextView) itemView.findViewById(R.id.idText);
            checkBox = (CheckBox) itemView.findViewById(R.id.grpCheckBox);


        }
    }
}

