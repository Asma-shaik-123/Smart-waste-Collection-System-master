//package com.example.smartwastagesystem;
//
//import android.os.Bundle;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class malert extends AppCompatActivity {
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_alert);
//    }
//}


package com.example.smartwastagesystem;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.smartwastagesystem.NotifyMessage;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class malert extends AppCompatActivity {

    Button button;
    Context context;
    CardView cardview;
    LayoutParams layoutparams;
    TextView textview;
    RelativeLayout relativeLayout;


    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Previous versions of Firebase
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_alert);

        context = getApplicationContext();


        mRef = new Firebase("https://smart-waste-collection-ef779-default-rtdb.asia-southeast1.firebasedatabase.app/number");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value= dataSnapshot.getValue().toString();
                // int valu = (500 / value)*100;


                Double finalValue=Double.parseDouble(value);
                Double val;
                val = (1-(finalValue/28))*100;


                if (finalValue >= 80){
                    showNotification();

                    relativeLayout = (RelativeLayout)findViewById(R.id.relativelayout1);


                    cardview = new CardView(context);

                    layoutparams = new LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT
                    );

                    cardview.setLayoutParams(layoutparams);

                    cardview.setRadius(15);

                    cardview.setPadding(25, 25, 25, 25);

                    cardview.setCardBackgroundColor(Color.MAGENTA);

                    cardview.setMaxCardElevation(30);

                    cardview.setMaxCardElevation(6);

                    textview = new TextView(context);

                    textview.setLayoutParams(layoutparams);

                    textview.setText("Wastage level of Bin 1 Reach:" + finalValue + "%" + "please Collect the garbage immediately.");

                    textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);

                    textview.setTextColor(Color.WHITE);

                    textview.setPadding(25,25,25,25);

                    textview.setGravity(Gravity.CENTER);

                    cardview.addView(textview);

                    relativeLayout.addView(cardview);

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
