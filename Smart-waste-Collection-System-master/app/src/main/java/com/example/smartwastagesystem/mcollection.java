package com.example.smartwastagesystem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import com.bcgdv.asia.lib.fanmenu.FanMenuButtons;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class mcollection extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcollection);

        //menu
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.myFAB);
        final FanMenuButtons sub = (FanMenuButtons) findViewById(R.id.myFABSubmenu);
        if (sub != null) {
            sub.setOnFanButtonClickListener(new FanMenuButtons.OnFanClickListener() {
                @Override
                public void onFanButtonClicked(int index) {

                    //its run on behaf of index value
                    if (index==0){
                        Intent acc= new Intent(mcollection.this, Account.class);
                        startActivity(acc);
                    }
                    else if(index==1){
                        Intent col= new Intent(mcollection.this,mcollection.class);
                        startActivity(col);
                    }
                    else if(index==2){
                        Intent rec = new Intent(mcollection.this,mrecord.class);
                        startActivity(rec);
                    }
                    else if (index==3) {
                        Intent  report = new Intent(mcollection.this, mreport.class);
                        startActivity(report);
                    }
                    else if (index==4){
                        Intent alert = new Intent(mcollection.this,malert.class);
                        startActivity(alert);
                    }
                    else if (index==5){
                        Intent contact = new Intent(mcollection.this , Contact.class);
                        startActivity(contact);
                    }
                    else if (index==6) {
                        Intent abt= new Intent(mcollection.this , About.class);
                        startActivity(abt);
                    }
                    else if (index==7){
                        Intent how = new Intent( mcollection.this , howto.class );
                        startActivity(how);
                    }

                }
            });

            if (fab != null) {
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sub.toggleShow();
                    }
                });
            }
        }
    }


    public void singlecollection(View view) {
        Intent j=new Intent(mcollection.this,map_single_collection.class);
        startActivity(j);
    }

    public void multicollection(View view) {
        Intent mapopen = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.co.in/maps/dir/prakash nagar,narasaraopet/Eswar college of Engineering,kesanupalli/16.231506, 80.068167"));
        mapopen.setPackage("com.google.android.apps.maps");
        if(mapopen.resolveActivity(getPackageManager())!=null){
            startActivity(mapopen);
        }

    }

    }
