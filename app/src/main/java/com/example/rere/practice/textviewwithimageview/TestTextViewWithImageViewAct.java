package com.example.rere.practice.textviewwithimageview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rere.practice.R;
import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.base.utils.TagLog;

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

        TextView textViewSpan = new TextView(mContext);
        textViewSpan.setTextSize(50);
        layout.addView(textViewSpan);
        setTextViewSpac(textViewSpan, getString(R.string.test_textview_withimageview));

    }

    private void setTextViewSpac(TextView textViewSpan, String text) {
        TagLog.i(TAG, "setTextViewSpac() : " + " textViewSpan = " + textViewSpan + ","
                + " text = " + text + ",");
        if (null == textViewSpan || TextUtils.isEmpty(text)) {
            return;
        }

        // getLineHeight
        TextPaint paint = textViewSpan.getPaint();
        float fontSpacing = paint.getFontSpacing();
        TagLog.i(TAG, "setTextViewSpac() : " + " fontSpacing = " + fontSpacing + ",");

        float sizeP = 0.7f;
        int left = 0;
        int top = (int) ((1 - sizeP) / 2 * fontSpacing);
        int right = (int) (sizeP * fontSpacing);
        int bottom = (int) (sizeP * fontSpacing + top);

        String finalText = text.concat(" ");
        Spannable span = new SpannableString(finalText);
        Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(left, top, right, bottom);
        Rect padding = new Rect();
        // TODO: 18-1-23 DrawablePadding
        boolean drawablePadding = drawable.getPadding(padding);
        TagLog.i(TAG, "setTextViewSpac() : " + " padding = " + padding + ","
                + " drawablePadding = " + drawablePadding + ",");
        ImageSpan image = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
        span.setSpan(image, finalText.length() - 1, finalText.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        textViewSpan.setText(span);

        tempText();
    }

    private TextViewWithImageView addTextViewWithImageView() {
        return new TextViewWithImageView(mContext);
    }

    private void tempText() {
        int num = 2006;
        int totalCount = 0;
        int canDivide = 0;
        int[] smallNum = new int[]{
                2, 17, 59//, 1003, 2006
        };
        for (int i = 1; i < 17; i++) {
            totalCount++;
            for (int j = 0; j < smallNum.length; j++) {
                if (i % smallNum[j] == 0) {
                    canDivide++;
                    break;
                }
            }
        }
        int result = totalCount - canDivide;
        TagLog.i(TAG, "tempText() : " + " totalCount = " + totalCount + ","
                + " canDivide = " + canDivide + "," + " result = " + result + ",");
    }
}
