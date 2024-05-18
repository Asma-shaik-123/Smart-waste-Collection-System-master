package com.example.smartwastagesystem;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.views.BannerSlider;


import java.util.ArrayList;
import java.util.List;

public class Dashboard_Activity extends AppCompatActivity {

    Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_dashboard);



        BannerSlider bannerSlider =  findViewById(R.id.banner_slider1);
        List<Banner> banners=new ArrayList<>();
        //add banner using image url
        /// banners.add(new RemoteBanner("Put banner image url here ..."));
        //add banner using resource drawable
        banners.add(new DrawableBanner(R.drawable.slider_1));
        banners.add(new DrawableBanner(R.drawable.slider_2));
        banners.add(new DrawableBanner(R.drawable.slider_3));
        banners.add(new DrawableBanner(R.drawable.slider_4));
        banners.add(new DrawableBanner(R.drawable.slider_8));
        banners.add(new DrawableBanner(R.drawable.slider_9));
        bannerSlider.setBanners(banners);



        mRef = new Firebase("https://smart-waste-collection-ef779-default-rtdb.asia-southeast1.firebasedatabase.app/number");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                String value= dataSnapshot.getValue().toString();



                Double finalValue=Double.parseDouble(value);
                Double val;
                val = (1-(finalValue/29))*100;



                if (finalValue >= 80){
                    showNotification();
                }
                else {
                    //do nothing
                }




            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




    }

    public void collection(View view) {
        Intent i=new Intent(Dashboard_Activity.this,mcollection.class);
        startActivity(i);
    }

    public void report(View view) {
        Intent i=new Intent(Dashboard_Activity.this,mreport.class);
        startActivity(i);
    }

    public void record(View view) {
        Intent i=new Intent(Dashboard_Activity.this,mrecord.class);
        startActivity(i);
    }

    public void alert(View view) {

        Intent i=new Intent(Dashboard_Activity.this,malert.class);
        startActivity(i);


    }

    public void contact(View view) {
        Intent i=new Intent(Dashboard_Activity.this,Contact.class);
        startActivity(i);
    }

    public void profile(View view) {
        Intent i=new Intent(Dashboard_Activity.this,Account.class);
        startActivity(i);
    }

    public void howto(View view) {
        Intent i=new Intent(Dashboard_Activity.this,howto.class);
        startActivity(i);
    }

    public void about(View view) {
        Intent i=new Intent(Dashboard_Activity.this,About.class);
        startActivity(i);
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

