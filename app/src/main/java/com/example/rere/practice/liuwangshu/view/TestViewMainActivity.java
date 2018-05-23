package com.example.rere.practice.liuwangshu.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.example.rere.practice.base.activity.TestBaseActivity;

/**
 * 刘望舒blog view体系
 *
 * Created by rere on 18-5-2.
 */

public class TestViewMainActivity extends TestBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestViewMainActivity.class));
    }

    @Override
    protected void addViews(LinearLayout layout) {
        CustomSlideView customSlideView = new CustomSlideView(mContext);
        customSlideView.setBackgroundColor(Color.RED);
        layout.addView(customSlideView, new LinearLayout.LayoutParams(80, 80));

        // animation 1
        // customSlideView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.translate));

        // animation 2
//        ObjectAnimator.ofFloat(customSlideView, "translationX", 0, 300)
//                .setDuration(1000).start();

        // call scoller
        customSlideView.smoothScrollTo(300, 0);

        // add a view to expand the linearLayout
        CustomSlideView view = new CustomSlideView(mContext);
        view.setBackgroundColor(Color.YELLOW);
        LinearLayout.LayoutParams params
                = new LinearLayout.LayoutParams(
                160, 160);
        layout.addView(view, params);
    }
}
