package com.pnf.elar.app.activity;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.pnf.elar.app.Drawer;
import com.pnf.elar.app.R;
import com.pnf.elar.app.SaveDraft;
import com.pnf.elar.app.UserSessionManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class FilterSaveDraftActivity extends Fragment {
TextView subFilterHeaderText;
    EditText filterEditText;
    CheckBox mineonlyCheckBox;
    TextView fromdatePicker, todatePicker,fromText,toText,mineonlyText;
    Button cancelBtn, filterBtn;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener fromDate;
    Calendar myCalendar2;
    DatePickerDialog.OnDateSetListener todate;

    View rootView;

    String lang;

    UserSessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_filter_save_draft, container, false);
/*
        setContentView(R.layout.activity_filter_save_draft);
*/

        initView(rootView);

/*
        ((Drawer)getActivity()).setVisible(false);
*/
/*
        if (lang.equalsIgnoreCase("sw")) {
*/
            ((Drawer) getActivity()).setActionBarTitle(getString(R.string.Filter));
      /*  } else {
            ((Drawer) getActivity()).setActionBarTitle("Edit post");
        }
*/
        myCalendar = Calendar.getInstance();
        fromDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateFromdate();
            }

        };

        myCalendar2 = Calendar.getInstance();
        todate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTODate();
            }

        };

        setClickListener();
        return rootView;

    }

    public void initView(View v) {

        subFilterHeaderText=(TextView)v.findViewById(R.id.subFilterHeaderText);
        fromText=(TextView)v.findViewById(R.id.fromText);
        toText=(TextView)v.findViewById(R.id.toText);
        mineonlyText=(TextView)v.findViewById(R.id.mineonlyText);

        filterEditText = (EditText) v.findViewById(R.id.filterEditText);
        mineonlyCheckBox = (CheckBox) v.findViewById(R.id.mineonlyCheckBox);

        fromdatePicker = (TextView) v.findViewById(R.id.fromdatePicker);
        todatePicker = (TextView) v.findViewById(R.id.todatePicker);

        cancelBtn = (Button) v.findViewById(R.id.cancelBtn);
        filterBtn = (Button) v.findViewById(R.id.filterBtn);

        getLanugaue();


    }
    public void getLanugaue()
    {
        session = new UserSessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);


        if(lang.equalsIgnoreCase("en"))
        {
            subFilterHeaderText.setText("Filter");
            filterEditText.setHint("Filter");
            fromText.setText("From");
            toText.setText("To");
            mineonlyText.setText("Mine only");
            cancelBtn.setText("CANCEL");
            filterBtn.setText("FILTER");


        }
        else
        {
            subFilterHeaderText.setText("Filtrera");
            filterEditText.setHint("Filtrera");
            fromText.setText("Från");
            toText.setText("Till");
            mineonlyText.setText("gruvan endast");
            cancelBtn.setText("ANNULLERA");
            filterBtn.setText("FILTRERA");




        }
    }

    public void setClickListener() {

        // ///////////
        // /////////////

        fromdatePicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), fromDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        todatePicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), todate, myCalendar2
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                SaveDraft rFragment = new SaveDraft();

                Bundle bundle = new Bundle();
                bundle.putString("my_draft", "'");
                bundle.putString("description_value", "");
                bundle.putString("from_date", "");
                bundle.putString("to_date", "");
                rFragment.setArguments(bundle);
                FragmentTransaction ft = fragmentManager
                        .beginTransaction();
                ft.replace(R.id.content_frame, rFragment);
                ft.commit();

            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mine_only = "";
                String decription_value = filterEditText.getText().toString().trim();


                if (mineonlyCheckBox.isChecked()) {
                    mine_only = "1";
                } else {

                }

                FragmentManager fragmentManager = getFragmentManager();
                SaveDraft rFragment = new SaveDraft();

                Bundle bundle = new Bundle();
                bundle.putString("my_draft", mine_only);
                bundle.putString("description_value", decription_value);
                bundle.putString("from_date", fromdatePicker.getText().toString());
                bundle.putString("to_date", todatePicker.getText().toString());
                rFragment.setArguments(bundle);
                FragmentTransaction ft = fragmentManager
                        .beginTransaction();
                ft.replace(R.id.content_frame, rFragment);
                ft.commit();

               /* FragmentManager fragmentManager = getFragmentManager();

				*//* Creating fragment instance *//*
                Publish rFragment = new Publish();

				*//* Passing selected item information to fragment *//*
                *//* Replace fragment *//*
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, rFragment);
                ft.commit();*/
            /*    {
                    Description = "";
                    "Mine_Only" = "";
                    "authentication_token" = 182d5c281d50a40929af8bd12c0c1b43b2ff6447;
                    "from_date" = "";
                    language = en;
                    securityKey = H67jdS7wwfh;
                    "to_date" = "2016-10-09";
                }
*/
              /*  params.add(new BasicNameValuePair("from_date", ""));
                params.add(new BasicNameValuePair("to_date", ""));

                params.add(new BasicNameValuePair("description_value", ""));
                params.add(new BasicNameValuePair("language", lang));
                params.add(new BasicNameValuePair("my_draft", ""));*/

            }
        });
    }

    public void updateFromdate() {

        String myFormat = "yyyy-MM-dd"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        fromdatePicker.setText(sdf.format(myCalendar.getTime()));
    }

    public void updateTODate() {

        String myFormat = "yyyy-MM-dd"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        todatePicker.setText(sdf.format(myCalendar2.getTime()));
    }
}
