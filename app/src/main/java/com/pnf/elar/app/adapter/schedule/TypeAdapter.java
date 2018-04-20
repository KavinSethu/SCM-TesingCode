package com.pnf.elar.app.adapter.schedule;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pnf.elar.app.Bo.schedule.CourseBean;
import com.pnf.elar.app.Bo.schedule.CourseBean;
import com.pnf.elar.app.R;

import java.util.List;

/**
 * Created by VKrishnasamy on 04-11-2016.
 */


public class TypeAdapter extends ArrayAdapter<CourseBean> {

    private Activity activity;
    private List<CourseBean> courseList;
    public Resources res;
    CourseBean CourseBean = null;
    LayoutInflater inflater;

    /*************
     * CustomAdapter Constructor
     *****************/
    public TypeAdapter(Activity activitySpinner,
                       int textViewResourceId,
                       List<CourseBean> courseList
    ) {
        super(activitySpinner, textViewResourceId, courseList);

        /********** Take passed values **********/
        activity = activitySpinner;
        this.courseList = courseList;

        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {
        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.item_course_spinner, parent, false);
        TextView nameText = (TextView) row.findViewById(R.id.nameText);
        TextView idText = (TextView) row.findViewById(R.id.idText);
        /***** Get each Model object from Arraylist ********/

        CourseBean = courseList.get(position);
              nameText.setText(CourseBean.getName());
        idText.setText(CourseBean.getId());

        return row;
    }
}