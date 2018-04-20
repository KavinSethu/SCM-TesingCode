package com.pnf.elar.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CurTagDetailsActivity extends AppCompatActivity {

    String tagTitle, tagDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cur_tag_details);


        ImageView closeTagImage = (ImageView) findViewById(R.id.closeTagImage);
        TextView tagHeaderText = (TextView) findViewById(R.id.tagHeaderText);
        final TextView tagDescrpText = (TextView) findViewById(R.id.tagDescrpText);

        tagTitle = getIntent().getStringExtra("tagTitle");
        tagDescription = getIntent().getStringExtra("tagDescrp");


        tagHeaderText.setText(tagTitle);
        tagDescrpText.setText(tagDescription);


        closeTagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
