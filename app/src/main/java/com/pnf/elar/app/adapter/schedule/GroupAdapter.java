package com.pnf.elar.app.adapter.schedule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.pnf.elar.app.Bo.schedule.CourseBean;
import com.pnf.elar.app.Bo.schedule.CourseBean;
import com.pnf.elar.app.R;
import com.pnf.elar.app.util.AppLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VKrishnasamy on 31-01-2017.
 */


public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    Context context;
    List<CourseBean> groupList = new ArrayList<>();

    public GroupAdapter(Context context, List<CourseBean> groupList) {
        this.context = context;
        this.groupList = groupList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_spinner, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {

            holder.nameText.setText(groupList.get(position).getName());
            holder.idText.setText(groupList.get(position).getId());
            holder.checkBox.setChecked(groupList.get(position).isSelected());
            holder.checkBox.setVisibility(View.VISIBLE);


            holder.nameText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (groupList.get(position).getId().equalsIgnoreCase("-1")) {
                        if (!groupList.get(position).isSelected()) {

                            for (int i = 0; i < groupList.size(); i++) {


                                if (groupList.get(i).isSelected() == true) {
                                    groupList.get(i).setSelected(false);

                                }
                            }

                            groupList.get(position).setSelected(true);

                        } else {
                            groupList.get(position).setSelected(false);

                        }
                        notifyDataSetChanged();
                    /*notify();*/
                    } else if (groupList.get(position).getId().equalsIgnoreCase("-2")) {
                        if (!groupList.get(position).isSelected()) {

                            for (int i = 0; i < groupList.size(); i++) {


                                if (groupList.get(i).isSelected() == true) {
                                    groupList.get(i).setSelected(false);
                                    notifyItemChanged(position);
                                }
                            }

                            groupList.get(position).setSelected(true);

                        } else {
                            groupList.get(position).setSelected(false);
                        }
                        notifyDataSetChanged();

                    } else {


                        if (groupList.get(groupList.size() - 1).isSelected()) {
                            groupList.get(groupList.size() - 1).setSelected(false);
                        }
                        if (groupList.get(groupList.size() - 2).isSelected()) {
                            groupList.get(groupList.size() - 2).setSelected(false);
                        }


                        if (groupList.get(position).isSelected()) {


                            groupList.get(position).setSelected(false);


                        } else {
                            groupList.get(position).setSelected(true);
                        }
                        notifyDataSetChanged();

                    }

                }
            });

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (groupList.get(position).getId().equalsIgnoreCase("-1")) {
                        if (!groupList.get(position).isSelected()) {

                            for (int i = 0; i < groupList.size(); i++) {


                                if (groupList.get(i).isSelected() == true) {
                                    groupList.get(i).setSelected(false);

                                }
                            }

                            groupList.get(position).setSelected(true);

                        } else {
                            groupList.get(position).setSelected(false);

                        }
                        notifyDataSetChanged();
                    /*notify();*/
                    } else if (groupList.get(position).getId().equalsIgnoreCase("-2")) {
                        if (!groupList.get(position).isSelected()) {

                            for (int i = 0; i < groupList.size(); i++) {


                                if (groupList.get(i).isSelected() == true) {
                                    groupList.get(i).setSelected(false);
                                    notifyItemChanged(position);
                                }
                            }

                            groupList.get(position).setSelected(true);

                        } else {
                            groupList.get(position).setSelected(false);
                        }
                        notifyDataSetChanged();

                    } else {


                        if (groupList.get(groupList.size() - 1).isSelected()) {
                            groupList.get(groupList.size() - 1).setSelected(false);
                        }
                        if (groupList.get(groupList.size() - 2).isSelected()) {
                            groupList.get(groupList.size() - 2).setSelected(false);
                        }


                        if (groupList.get(position).isSelected()) {


                            groupList.get(position).setSelected(false);


                        } else {
                            groupList.get(position).setSelected(true);
                        }
                        notifyDataSetChanged();

                    }

                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder

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

