package com.example.rere.practice.callapp;

import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.base.utils.TagLog;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;

/**
 * test call other app
 *
 * Created by rere on 2017-7-5.
 */

public class TestCallAppActivity extends TestBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestCallAppActivity.class));
    }

    @Override
    protected void addViews(LinearLayout layout) {
        super.addViews(layout);
        getButton(layout, "call ccb app by url", v -> {
            // call ccb app by url
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // String url = "ccbapp://utils?ccbEncodeParam=7kzjqtSTUIQcS4R2tWrVOORlE5366ZZLw052XsziYyEO9LgTBcnyNc8%2BqDLZc7isH99L97ufSm7Pj3eU";
            // Uri uri = Uri.parse(url);
            // intent.setData(uri);

            intent.setClassName("com.chinamworld.main", "com.ccb.start.StartActivity");
//            intent.setComponent(new ComponentName(mContext, "com.chinamworld.main/com.ccb.start.StartActivity"));

            try {
                startActivity(intent);
            } catch (Exception e) {
                TagLog.e(TAG, e.getMessage());
            }
        });
    }
}
            // same url is not ok.

            // only this one isnot ok
            // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);//add this is ok

            // add random is ok.
            //url += (System.currentTimeMillis());//"&time=" +
