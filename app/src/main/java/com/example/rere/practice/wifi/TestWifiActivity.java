package com.example.rere.practice.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.base.utils.TagLog;

import java.util.List;

/**
 * Created by rere on 18-5-22.
 */

public class TestWifiActivity extends TestBaseActivity {

    private static final String TAG = TestWifiActivity.class.getSimpleName();
    private WifiManager wifi;
    private WifiScanReceiver wifiReceiver;

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestWifiActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiReceiver = new WifiScanReceiver();
    }

    @Override
    protected void addViews(LinearLayout layout) {
        getButton(layout, "wifi start scan", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // wifi start scan
                TagLog.i(TAG, "onClick() : " + "wifi start scan");
                wifi.startScan();
            }
        });
    }

    private class WifiScanReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {
            TagLog.i(TAG, "wifi scan onReceive() : ");
            List<ScanResult> wifiScanList = wifi.getScanResults();
            for(int i = 0; i < wifiScanList.size(); i++){
                String info = ((wifiScanList.get(i)).toString());

                TagLog.i(TAG, "onReceive() : " + " info = " + info + ",");
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        TagLog.i(TAG, "onPause() : " + "unregisterReceiver");
        unregisterReceiver(wifiReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TagLog.i(TAG, "onResume() : " + "registerReceiver");
        registerReceiver(
                wifiReceiver,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        );
    }
}
