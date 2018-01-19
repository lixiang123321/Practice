package com.example.rere.practice.textviewwithimageview;

import com.example.rere.practice.R;
import com.example.rere.practice.base.activity.TestBaseActivity;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;

/**
 * Created by rere on 18-1-17.
 */

public class TestTextViewWithImageViewAct extends TestBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestTextViewWithImageViewAct.class));
    }

    @Override
    protected void addViews(LinearLayout layout) {
        TextViewWithImageView textView = addTextViewWithImageView();
        textView.setText(R.string.test_textview_withimageview);
        textView.setTextSize(50);
        textView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        layout.addView(textView);
    }

    private TextViewWithImageView addTextViewWithImageView() {
        return new TextViewWithImageView(mContext);
    }
}
