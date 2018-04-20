package com.pnf.elar.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pnf.elar.app.R;

import java.util.List;

public class SchoolAdapter extends ArrayAdapter<SearchSchoolBo> {
    TextView schoolText,schoolDomainText;
    private final Activity context;
    private final List<SearchSchoolBo> searchedSchool;

    public SchoolAdapter(Activity edit_post, List<SearchSchoolBo> searchedSchool
    ) {
        // TODO Auto-generated constructor stub
        super(edit_post, R.layout.item_schools, searchedSchool);
        this.context = edit_post;
        this.searchedSchool = searchedSchool;


    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {


        LayoutInflater inflater = context.getLayoutInflater();


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_schools, parent, false);

        schoolText = (TextView) rowView.findViewById(R.id.schoolText);
        schoolDomainText=(TextView)rowView.findViewById(R.id.schoolDomainText);




        schoolText.setText(searchedSchool.get(position).getName());

        schoolDomainText.setText(searchedSchool.get(position).getDomain_name());


        return rowView;
    }
}