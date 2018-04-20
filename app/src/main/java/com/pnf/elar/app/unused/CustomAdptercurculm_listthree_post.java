package com.pnf.elar.app.unused;

import java.util.ArrayList;
import java.util.List;

import com.pnf.elar.app.Bo.CurriculamEduPost;
import com.pnf.elar.app.ImageLoadernew;
import com.pnf.elar.app.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class CustomAdptercurculm_listthree_post extends ArrayAdapter<CurriculamEduPost.SubchildrenEntity> {

    private final Context cont;
    private final String[] chi;
    ArrayList<String> ids = new ArrayList<String>();
    String[] stockArr;
    boolean checkAll_flag = false;
    boolean checkItem_flag = false;
    private static LayoutInflater inflater = null;
    public ImageLoadernew imageLoader;

    List<CurriculamEduPost.SubchildrenEntity> subChildList=new ArrayList<>();


    static class ViewHolder {
        protected TextView text;
        protected CheckBox checkbox;
    }

    public CustomAdptercurculm_listthree_post(Context curricu, String[] curriclm_tg, List<CurriculamEduPost.SubchildrenEntity> subChildList) {
        super(curricu, R.layout.curriculamtagpost_lsitthree, subChildList);
        this.cont = curricu;
        this.chi = curriclm_tg;
        this.subChildList=subChildList;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (view == null) {
            LayoutInflater inflator = ((Activity) cont).getLayoutInflater();
            view = inflator.inflate(R.layout.curriculamtagpost_lsitthree, null);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.label);

            view.setTag(viewHolder);
            view.setTag(R.id.label, viewHolder.text);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.text.setTextSize(10);

        viewHolder.text.setText(subChildList.get(position).getTitle());


        return view;
    }
}
