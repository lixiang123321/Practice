package com.example.rere.practice.liuwangshu;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.liuwangshu.view.TestViewMainActivity;

/**
 * 刘望舒blog view体系
 *
 * Created by rere on 18-5-2.
 */

public class TestLiuWangShuPracticeActivity extends TestBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestLiuWangShuPracticeActivity.class));
    }

    @Override
    protected void addViews(LinearLayout layout) {
        getButton(layout, "view practices", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // view practices
                TestViewMainActivity.start(mContext);
            }
        });
    }
}
