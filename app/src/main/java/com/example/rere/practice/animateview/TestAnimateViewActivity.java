package com.example.rere.practice.animateview;

import com.example.rere.practice.R;
import com.example.rere.practice.base.activity.TestBaseActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by rere on 18-1-5.
 */

public class TestAnimateViewActivity extends TestBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestAnimateViewActivity.class));
    }

    @Override
    protected void addViews(LinearLayout layout) {
        AppOpenWithAnimateView animateView = getAnimateView();
        layout.addView(animateView,
                new LinearLayout.LayoutParams(240, 498));

        getButton(layout, "animate start", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // animate
                animateView.startAnimate();
            }
        });

        getButton(layout, "animate reset", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // animate
                animateView.resetAnimate();
            }
        });

        getButton(layout, "animate roll back", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // animate roll back
                animateView.rollbackAnimate();
            }
        });

        getButton(layout, "set moving res", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set moving res
                animateView.setViewMovingRes(R.mipmap.inside_login);
            }
        });
    }

    private AppOpenWithAnimateView getAnimateView() {
        return new AppOpenWithAnimateView(mContext);
    }


}
