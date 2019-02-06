package com.example.rere.practice.main;

import com.example.rere.practice.animateview.TestAnimateViewActivity;
import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.base.utils.TagLog;
import com.example.rere.practice.callapp.TestCallAppActivity;
import com.example.rere.practice.concurrent.TestLocksActivity;
import com.example.rere.practice.concurrent.TestSynchronizationActivity;
import com.example.rere.practice.concurrent.TestThreadAndExecutorActivity;
import com.example.rere.practice.encrypt.TestEncryptAct;
import com.example.rere.practice.java8.TestJava8Activity;
import com.example.rere.practice.launchmode.base.TestLaunchModeBaseActivity;
import com.example.rere.practice.liuwangshu.TestLiuWangShuPracticeActivity;
import com.example.rere.practice.network.TestNetworkAct;
import com.example.rere.practice.reverse.TestReverseEngineeringActivity;
import com.example.rere.practice.sms.SmsUtils;
import com.example.rere.practice.testdistinct.TestDistinctAct;
import com.example.rere.practice.textviewwithimageview.TestTextViewWithImageViewAct;
import com.example.rere.practice.wifi.TestWifiActivity;
import com.example.rere.practice.wifi.TestWifiUtils;
import com.example.rere.practice.xposed.test.TestInVirtualXposedActivity;
import com.example.rere.practice.xposed.test.TestInVirtualXposedActivity2;
import com.example.rere.practice.xposed.test.TestInVirtualXposedEnvironment;
import com.example.rere.practice.xposed.xposedwifi.XposedWifiActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;

/**
 * main activity
 *
 * Created by rere on 2017/1/20.
 */
public class MainActivity extends TestBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void addViews(LinearLayout layout) {
        getButton(layout, "java 8 character", v -> {
            // java 8 character
            TestJava8Activity.start(mContext);
        });

        getButton(layout, "concurrency practice part 1 : thread and executors", v -> {
            // concurrency practice part 1 : thread and executors
            TestThreadAndExecutorActivity.start(mContext);
        });

        getButton(layout, "concurrency practice part 2 : synchronization ", v -> {
            // concurrency practice part 2 : synchronization
            TestSynchronizationActivity.start(mContext);
        });

        getButton(layout, "concurrency practice part 2 : locks", v -> {
            // concurrency practice part 2 : locks
            TestLocksActivity.start(mContext);
        });


        getButton(layout, "call other app test", v -> {
            // call other app test
            TestCallAppActivity.start(mContext);

        });


        getButton(layout, "test distinctAct", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // test distinctAct
                TestDistinctAct.start(mContext);
            }
        });

        getButton(layout, "test App OpenView", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // test App OpenView
                TestAnimateViewActivity.start(mContext);
            }
        });

        getButton(layout, "test textview end with imageView", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // test textview end with imageView
                TestTextViewWithImageViewAct.start(mContext);
            }
        });

        getButton(layout, "test in virtual Xposed Activity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // test in virtual Xposed Activity
                TestInVirtualXposedActivity.start(mContext);
            }
        });

        getButton(layout, "test in virtual Xposed Activity2", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // test in virtual Xposed Activity2
                TestInVirtualXposedActivity2.start(mContext);
            }
        });

        getButton(layout, "Xposed wifi activity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xposed wifi activity
                XposedWifiActivity.start(mContext);
            }
        });

        getButton(layout, "send sms test", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send sms test
                SmsUtils.jumpSmsEditor(mContext, "95533", "52067");
            }
        });

        getButton(layout, "reverse engineering", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // reverse engineering
                TestReverseEngineeringActivity.start(mContext);
            }
        });

        getButton(layout, "liuwangshu practice", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // liuwangshu practice
                TestLiuWangShuPracticeActivity.start(mContext);
            }
        });

        getButton(layout, "get wifi info", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get wifi info

                String[] permission = new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.CHANGE_WIFI_STATE,
                };

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permission, 101);
                } else {
                    LinearLayout mLayoutWifi = new LinearLayout(mContext);
                    mLayoutWifi.setOrientation(LinearLayout.VERTICAL);
                    layout.addView(mLayoutWifi,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    TestWifiUtils.getWifiInfo(mContext, mLayoutWifi);
                }
            }
        });

        getButton(layout, "get GPS", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get GPS
                testGPS(layout);
            }
        });

        getButton(layout, "jump get wifi act", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // jump get wifi act
                TestWifiActivity.start(mContext);
            }
        });

        getButton(layout, "test in virtual Xposed environment", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // test in virtual Xposed environment
                TestInVirtualXposedEnvironment.start(mContext);
            }
        });

        getButton(layout, "Test LaunchMode acts", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Test LaunchMode acts
                TestLaunchModeBaseActivity.start(mContext);
            }
        }).performClick();

        getButton(layout, "TestNetworkAct", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                TestNetworkAct.start(mContext);
            }
        });

        getButton(layout, "TestEncryptAct", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TestEncryptAct
                TestEncryptAct.start(mContext);
            }
        }).performClick();

    }

    private void testGPS(LinearLayout layout) {
        /*LocationManager mLocationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);*/
        /*try {
            Location locationGPS = mLocationManager.getLocation(LocationManager.GPS_PROVIDER);
            Location locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } catch (Throwable throwable) {

        }*/
        //Location location = new Location();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (101 == requestCode && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            TestWifiUtils.getWifiInfo(mContext, null);
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    TagLog.i(TAG, "wifi scan onReceive() : " + " intent = " + intent + ",");
                    TestWifiUtils.getWifiInfo(context, null);
                /*if (finalIsWifiOpenByUs) {
                    wifiManager.setWifiEnabled(false);
                }*/
                    context.unregisterReceiver(this);
                }
            }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        }
    }
}
