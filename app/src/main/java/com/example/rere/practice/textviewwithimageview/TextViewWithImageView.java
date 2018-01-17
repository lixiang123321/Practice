package com.example.rere.practice.textviewwithimageview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by rere on 18-1-17.
 */
public class TextViewWithImageView extends android.support.v7.widget.AppCompatTextView {


    private Context mContext;

    public TextViewWithImageView(Context context) {
        super(context);
        init(context);
    }

    public TextViewWithImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextViewWithImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 测量文字区域和最后一行文字的位置。
    }
}
