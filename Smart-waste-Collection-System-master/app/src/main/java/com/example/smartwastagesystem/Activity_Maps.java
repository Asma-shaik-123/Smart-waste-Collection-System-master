package com.example.smartwastagesystem;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.location.LocationListenerCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;

public class Activity_Maps extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_layout);

        WebView mapview = findViewById(R.id.map_view);
        mapview.setWebViewClient(new WebViewClient());
        mapview.loadUrl("https://www.google.co.in/maps/dir/Your location/prakash nagar,narasaraopet/Eswar college of Engineering,kesanupalli/16.231506, 80.068167");
        mapview.getSettings().setJavaScriptEnabled(true);


    }
}