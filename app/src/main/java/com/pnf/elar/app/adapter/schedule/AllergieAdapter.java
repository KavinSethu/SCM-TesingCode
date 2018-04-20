package com.pnf.elar.app.adapter.schedule;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pnf.elar.app.Bo.schedule.FoodNoteBo;
import com.pnf.elar.app.Bo.schedule.ScheduleMonthList;
import com.pnf.elar.app.R;
import com.pnf.elar.app.views.ImageViewTouch;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;




/**
 * Created by VKrishnasamy on 28-12-2016.
 */


public class AllergieAdapter extends RecyclerView.Adapter<AllergieAdapter.AllergieViewHolder> {

    View itemView;
    LayoutInflater layoutInflater;
    List<FoodNoteBo.FinalStudentsEntity> allergieList = new ArrayList<>();
    Context ctx;
    String base_url;

    public AllergieAdapter(Context context, List<FoodNoteBo.FinalStudentsEntity> allergieList, String base_url) {
        this.ctx = context;
        this.allergieList = allergieList;
        this.layoutInflater = LayoutInflater.from(ctx);
        this.base_url = base_url;


    }

    @Override
    public AllergieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
/*
        itemView = layoutInflater.inflate(R.layout.item_allergies, parent, false);
*/
        itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allergies, parent, false);
        return new AllergieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AllergieViewHolder holder, int position) {
        try {

            FoodNoteBo.FinalStudentsEntity finalStudentsEntity = allergieList.get(position);

            holder.childNameText.setText(finalStudentsEntity.getFirst_name()+"\n"+finalStudentsEntity.getLast_name());
            holder.allergieNameText.setText(finalStudentsEntity.getAllergy_name());
            holder.allergieDescText.setText(finalStudentsEntity.getFree_text());

            Picasso.with(ctx)
                    .load(Uri.parse(base_url+finalStudentsEntity.getImage()))
                    .placeholder(R.drawable.child_allergie)
                    // optional
                    .error(R.drawable.child_allergie)         // optional
                    .into(holder.childImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return allergieList.size();
    }

    public static class AllergieViewHolder extends RecyclerView.ViewHolder {

        TextView childNameText, allergieNameText, allergieDescText;
        ImageView childImageView;

        AllergieViewHolder(View itemView) {
            super(itemView);
            childNameText = (TextView) itemView.findViewById(R.id.childNameText);
            allergieNameText = (TextView) itemView.findViewById(R.id.allergieNameText);
            allergieDescText = (TextView) itemView.findViewById(R.id.allergieDescText);
            childImageView = (ImageView) itemView.findViewById(R.id.childImageView);
        }


    }
}
