package com.example.rere.practice.xposed.test;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.base.utils.TagLog;
import com.example.rere.practice.base.utils.ValidControlUtils;

/**
 * Created by rere on 18-7-2.
 */

public class TestVirtualMultiAppActivity extends TestBaseActivity {

    private static final int KEY_PERMISSIONS_REQUEST = 1001;

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestVirtualMultiAppActivity.class));
    }

    @Override
    protected void addViews(LinearLayout layout) {
        getButton(layout, "get device info", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get device info
                final String[] permissions = new String[]{
                        Manifest.permission.READ_PHONE_STATE,
                };
                checkPermissionCheck(KEY_PERMISSIONS_REQUEST, permissions);
            }
        });

        getButton(layout, "is valid", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = ValidControlUtils.isValid();
                TagLog.i(TAG, "onClick() : " + " isValid = " + isValid + ",");
            }
        });
    }

    protected void onHasPermissionsDo() {
        getDeviceID();
    }

    @SuppressLint("MissingPermission")
    private void getDeviceID() {
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

        final String deviceId, serialNumber, androidId;
        deviceId = "" + tm.getDeviceId();
        serialNumber = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        String packageName = getPackageName();
        TagLog.i(TAG, "getDeviceID() : " + " packageName = " + packageName + ",");
        TagLog.i(TAG, "getDeviceID() : " + " deviceId = " + deviceId + ",");
        TagLog.i(TAG, "getDeviceID() : " + " serialNumber = " + serialNumber + ",");
        TagLog.i(TAG, "getDeviceID() : " + " androidId = " + androidId + ",");
    }

    protected void checkPermissionCheck(int requestCode, String[] permissions) {
        TagLog.i(TAG, "checkPermissionCheck() : ");
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mContext, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                TagLog.i(TAG, "checkPermissionCheck() : request permissions");
                ActivityCompat.requestPermissions(this,
                        permissions,
                        requestCode);
                return;
            }
        }
        onHasPermissionsDo();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case KEY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onHasPermissionsDo();
                } else {
                    Toast.makeText(mContext, "permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

}
