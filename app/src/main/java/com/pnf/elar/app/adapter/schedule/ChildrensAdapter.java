


/**
 * Created by Administrator on 2/28/2017.
 */


package com.pnf.elar.app.adapter.schedule;

        import android.app.Activity;
        import android.content.Context;
        import android.content.res.Resources;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
        import android.widget.TextView;

        import com.pnf.elar.app.Bo.schedule.AspectResponseBo;
        import com.pnf.elar.app.Bo.schedule.ChildrensBo;
        import com.pnf.elar.app.Bo.schedule.CourseBean;
        import com.pnf.elar.app.R;
        import com.pnf.elar.app.util.AppLog;

        import java.util.ArrayList;
        import java.util.List;




public class ChildrensAdapter extends RecyclerView.Adapter<ChildrensAdapter.AspectHolder> {

    Context context;
    List<ChildrensBo.ChildrenBean> aspectList=new ArrayList<>();

    public ChildrensAdapter(Context context, List<ChildrensBo.ChildrenBean> aspectList)
    {
        super();
        this.context=context;
        this.aspectList=aspectList;


    }


    @Override
    public AspectHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_spinner,parent,false);
        return new AspectHolder(row);
    }

    @Override
    public void onBindViewHolder(AspectHolder holder, final int position) {

        holder.nameText.setText(aspectList.get(position).getUSR_FirstName()+" "+aspectList.get(position).getUSR_LastName());
        holder.idText.setText(aspectList.get(position).getId());
        holder.checkBox.setChecked(aspectList.get(position).isSelected());


        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                AppLog.Log("bool ",b+"");
               /* if(b)
                {*/


                aspectList.get(position).setSelected(b);
/*
                }
*/
            }
        });

       /* holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(aspectList.get(position).getAspect().isSelected())
                {
                    aspectList.get(position).getAspect().setSelected(false);
                }
                else
                {
                    aspectList.get(position).getAspect().setSelected(false);

                }

            }
        });*/
        holder.checkBox.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return aspectList.size();
    }


    public class  AspectHolder extends   RecyclerView.ViewHolder

    {
        TextView nameText;
        TextView idText;
        CheckBox checkBox;


        public AspectHolder(View itemView) {
            super(itemView);

            nameText = (TextView) itemView.findViewById(R.id.nameText);
            idText = (TextView) itemView.findViewById(R.id.idText);
            checkBox = (CheckBox) itemView.findViewById(R.id.grpCheckBox);


        }
    }
}





  /*  View row = inflater.inflate(R.layout.item_course_spinner, parent, false);

    try
    {
        TextView nameText = (TextView) row.findViewById(R.id.nameText);
        TextView idText = (TextView) row.findViewById(R.id.idText);
        CheckBox grpCheckBox = (CheckBox) row.findViewById(R.id.grpCheckBox);
        *//***** Get each Model object from Arraylist ********//*

        ChildrensBo.ChildrenBean   aspectsEntity = aspectList.get(position);
        grpCheckBox.setVisibility(View.GONE);
        nameText.setText(aspectsEntity.getAspect().getName());
        idText.setText(aspectsEntity.getAspect().getName());
*/