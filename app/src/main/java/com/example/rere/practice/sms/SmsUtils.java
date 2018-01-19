package com.example.rere.practice.sms;

import com.example.rere.practice.base.utils.TagLog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by rere on 18-1-19.
 */

public class SmsUtils {

    private static final String TAG = SmsUtils.class.getSimpleName();

    public static void jumpSmsEditor(Context context,
                                     String phoneNum, String smsText) {
        try {
            jumpSmsEditorNoCheck(context, phoneNum, smsText);
        } catch (Exception e) {
            TagLog.e(TAG, "jumpSmsEditor() : " + e.getMessage());
        }
    }

    private static void jumpSmsEditorNoCheck(Context context,
                                             String phoneNum, String smsText) throws Exception {
        TagLog.i(TAG, "jumpSmsEditorNoCheck() : " + " context = " + context + ","
                + " phoneNum = " + phoneNum + "," + " smsText = " + smsText + ","
        );

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:" + phoneNum));
        sendIntent.putExtra("sms_body", smsText);
        context.startActivity(sendIntent);
        TagLog.i(TAG, "jumpSmsEditorNoCheck() : " + "jump sms Editor success.");
    }

}
