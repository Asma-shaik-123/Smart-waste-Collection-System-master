//package com.example.smartwastagesystem;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class mrecord extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mrecord);
//    }
//
//    }



package com.example.smartwastagesystem;



import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.bcgdv.asia.lib.fanmenu.FanMenuButtons;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.macroyau.thingspeakandroid.ThingSpeakChannel;
import com.macroyau.thingspeakandroid.ThingSpeakLineChart;
import com.macroyau.thingspeakandroid.model.ChannelFeed;

import java.util.Calendar;
import java.util.Date;

import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class mrecord extends AppCompatActivity {

    private ThingSpeakChannel tsChannel;
    private ThingSpeakLineChart tsChart;
    private LineChartView chartView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrecord);


        // Connect to ThinkSpeak Channel433450
        // Connect to ThinkSpeak Channel 9
        tsChannel = new ThingSpeakChannel(1970089);
        // Set listener for Channel feed update events
        tsChannel.setChannelFeedUpdateListener(new ThingSpeakChannel.ChannelFeedUpdateListener() {
            @Override
            public void onChannelFeedUpdated(long channelId, String channelName, ChannelFeed channelFeed) {
                // Show Channel ID and name on the Action Bar

                getSupportActionBar().setTitle(channelName);
                getSupportActionBar().setSubtitle("Channel " + channelId);
                // Notify last update time of the Channel feed through a Toast message
                Date lastUpdate = channelFeed.getChannel().getUpdatedAt();
                Toast.makeText(mrecord.this, lastUpdate.toString(), Toast.LENGTH_LONG).show();
            }
        });
        // Fetch the specific Channel feed
        tsChannel.loadChannelFeed();

        // Create a Calendar object dated 5 minutes ago
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -5);

        // Configure LineChartView
        chartView = findViewById(R.id.chart);
        chartView.setZoomEnabled(true);
        chartView.setValueSelectionEnabled(true);

        // Create a line chart from Field1 of ThinkSpeak Channel 9
        tsChart = new ThingSpeakLineChart(9, 1);
        // Get 200 entries at maximum
        tsChart.setNumberOfEntries(200);
        // Set value axis labels on 10-unit interval
        tsChart.setValueAxisLabelInterval(10);
        // Set date axis labels on 5-minute interval
        tsChart.setDateAxisLabelInterval(1);
        // Show the line as a cubic spline
        tsChart.useSpline(true);
        // Set the line color
        tsChart.setLineColor(Color.parseColor("#D32F2F"));
        // Set the axis color
        tsChart.setAxisColor(Color.parseColor("#455a64"));
        // Set the starting date (5 minutes ago) for the default viewport of the chart
        tsChart.setChartStartDate(calendar.getTime());
        // Set listener for chart data update
        tsChart.setListener(new  ThingSpeakLineChart.ChartDataUpdateListener() {
            @Override
            public void onChartDataUpdated(long channelId, int fieldId, String title, LineChartData lineChartData, Viewport maxViewport, Viewport initialViewport) {
                // Set chart data to the LineChartView
                chartView.setLineChartData(lineChartData);
                // Set scrolling bounds of the chart
                chartView.setMaximumViewport(maxViewport);
                // Set the initial chart bounds
                chartView.setCurrentViewport(initialViewport);
            }
        });
        // Load chart data asynchronously
        tsChart.loadChartData();


        //menu
        final FloatingActionButton fab = findViewById(R.id.myFAB1);
        final FanMenuButtons sub = findViewById(R.id.myFABSubmenu1);
        if (sub != null) {
            sub.setOnFanButtonClickListener(index -> {

                //its run on behaf of index value
                if (index==0){
                    Intent acc= new Intent(mrecord.this, Account.class);
                    startActivity(acc);
                }
                else if(index==1){
                    Intent col= new Intent(mrecord.this,mcollection.class);
                    startActivity(col);
                }
                else if(index==2){
                    Intent rec = new Intent(mrecord.this,mrecord.class);
                    startActivity(rec);
                }
                else if (index==3) {
                    Intent  report = new Intent(mrecord.this, mreport.class);
                    startActivity(report);
                }
                else if (index==4){
                    Intent alert = new Intent(mrecord.this,malert.class);
                    startActivity(alert);
                }
                else if (index==5){
                    Intent contact = new Intent(mrecord.this , Contact.class);
                    startActivity(contact);
                }
//                    else if (index==6) {
//                        Intent abt= new Intent(mrecord.this , About.class);
//                        startActivity(abt);
//                    }
//                    else if (index==7){
//                        Intent how = new Intent( mrecord.this , howto.class );
//                        startActivity(how);
//                    }

            });

            if (fab != null) {
                fab.setOnClickListener(v -> sub.toggleShow());
            }
        }
    }
}

