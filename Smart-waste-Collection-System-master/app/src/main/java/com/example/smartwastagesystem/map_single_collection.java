package com.example.smartwastagesystem;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bcgdv.asia.lib.fanmenu.FanMenuButtons;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.mapbox.mapboxsdk.geometry.LatLng;
//import com.tallygo.tallygoandroid.activities.turnbyturn.TGTurnByTurnActivity;
//import com.tallygo.tallygoandroid.fragments.navigation.base.TGBaseTurnByTurnFragment;
//import com.tallygo.tallygoandroid.sdk.navigation.TGRouteRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.pawelkleczkowski.customgauge.CustomGauge;


public class map_single_collection extends AppCompatActivity {


    private TextView garbage;
    private Firebase mRef;
    private CustomGauge gauge2;


    private String Long,lat;
    int i;
    private TextView text1;
    private TextView text2;


    NiftyDialogBuilder materialDesignAnimatedDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Previous versions of Firebase
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_map_single_collection);


        LinearLayout smc = (LinearLayout) findViewById(R.id.ll);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.fade_out_animation);

        smc.startAnimation(myAnim);


        materialDesignAnimatedDialog = NiftyDialogBuilder.getInstance(this);




        //retrive data from firebase
        garbage = (TextView) findViewById(R.id.progress_circle_text1);


        mRef = new Firebase("https://smart-waste-collection-ef779-default-rtdb.asia-southeast1.firebasedatabase.app/number");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String value = dataSnapshot.getValue().toString();


                Double finalValue = Double.parseDouble(value);
                Double val;
                val = (1 - (finalValue / 29)) * 100;

                if (finalValue <= 100 && finalValue >= 29) {

                    // int valu = (500 / value)*100;

                    ProgressBar simpleProgressBar = (ProgressBar) findViewById(R.id.progressBar1); // initiate the progress bar
                    simpleProgressBar.setMax(100); // 100 maximum value for the progress value
                    Double finalValue1 = Double.parseDouble(value);
                    Double val1;
                    val1 = (1 - (finalValue1 / 29)) * 100;
                    simpleProgressBar.setProgress((int) Double.parseDouble(String.valueOf(finalValue1))); // 50 default progress value for the progress bar
                    garbage.setText(finalValue1 + "%");

                    //                   garbage.setText(String.valueOf(val));
//                    Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/digit.ttf");
//                    garbage.setTypeface(custom_font);


                } else {

                    //do nothing
                }


                if (finalValue >= 80) {
                    showNotification();
                } else {
                    //do nothing
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        //menu
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.myFAB);
        final FanMenuButtons sub = (FanMenuButtons) findViewById(R.id.myFABSubmenu);
        if (sub != null) {
            sub.setOnFanButtonClickListener(new FanMenuButtons.OnFanClickListener() {
                @Override
                public void onFanButtonClicked(int index) {

                    //its run on behaf of index value
                    if (index == 0) {
                        Intent acc = new Intent(map_single_collection.this, Account.class);
                        startActivity(acc);
                    } else if (index == 1) {
                        Intent col = new Intent(map_single_collection.this, mcollection.class);
                        startActivity(col);
                    } else if (index == 2) {
                        Intent rec = new Intent(map_single_collection.this, mrecord.class);
                        startActivity(rec);
                    } else if (index == 3) {
                        Intent report = new Intent(map_single_collection.this, mreport.class);
                        startActivity(report);
                    } else if (index == 4) {
                        Intent alert = new Intent(map_single_collection.this, malert.class);
                        startActivity(alert);
                    } else if (index == 5) {
                        Intent contact = new Intent(map_single_collection.this, Contact.class);
                        startActivity(contact);
                    } else if (index == 6) {
                        Intent abt = new Intent(map_single_collection.this, About.class);
                        startActivity(abt);
                    } else if (index == 7) {
                        Intent how = new Intent(map_single_collection.this, howto.class);
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




    public void animatedDialog1(View view) {
        materialDesignAnimatedDialog
                .withTitle("Bin 1")
                .withMessage("Location:Comsats Wah cantt. \nClick on start button if you want to collect the garbage.")
                .withDialogColor("#00cccc")
                .withButton1Text("Start")
                .withButton2Text("Cancel")
                .withDuration(700)
                .withEffect(Effectstype.Fall)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //tallugo api

//                        google.navigation:q=22.652938,88.435538,17.3850,78.4867&mode=l

                        Intent mapopen = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.co.in/maps/dir/Eswar college of Engineering,kesanupalli/16.231506, 80.068167"));
                        mapopen.setPackage("com.google.android.apps.maps");
                        if(mapopen.resolveActivity(getPackageManager())!=null){
                            startActivity(mapopen);
                        }
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDesignAnimatedDialog.cancel();
                    }
                })
                .show();


    }



    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void showNotification() {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, NotifyMessage.class), 0);
        Resources r = getResources();
        Notification notification = new Notification.Builder(this)
                .setTicker("Smart Waste Collection")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("Smart Waste Collection")
                .setContentText("Waste reach the High Level please collect waste immediately")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
