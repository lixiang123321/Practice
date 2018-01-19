package com.example.rere.practice.textviewwithimageview;

import com.example.rere.practice.base.utils.TagLog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;

/**
 * Created by rere on 18-1-17.
 */
public class TextViewWithImageView extends android.support.v7.widget.AppCompatTextView {

    private static final String TAG = TextViewWithImageView.class.getSimpleName();

    private Context mContext;

    private boolean isShouldAddOneMoreLine = false;
    private int mWidth;
    private int mHeight;

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

    private Paint mDebugDrawPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 测量文字区域和最后一行文字的位置。
        TextPaint textPaint = getPaint();

        CharSequence text = getText();

        int start = 0;
        int end = length();

        int maxWidth = getRight() - getLeft() - getCompoundPaddingLeft() - getCompoundPaddingRight();
        float[] measuredWidth = new float[1];

        int measureCount = 0;
        CharSequence subCharSeq;
        float lastLineTextWidth = 0f;
        int lineCounts = 0;

        // debug
        //maxWidth = 200;

        TagLog.i(TAG, "onDraw() : "
                + " text = " + text + ","
                + " start = " + start + ","
                + " end = " + end + ","
                + " maxWidth = " + maxWidth + ","
        );

        for (; start < end; ) {

            measureCount = textPaint.breakText(text, start, end, true, maxWidth, measuredWidth);
            // measureCount chars has measure.

            subCharSeq = text.subSequence(start, start + measureCount);
            lastLineTextWidth = measuredWidth[0];

            TagLog.i(TAG, "onDraw() : "
                    + " subCharSeq = " + subCharSeq + ","
                    + " start = " + start + ","
                    + " (start + measureCount) = " + (start + measureCount) + ","
                    + " measureCount = " + measureCount + ","
                    + " measuredWidth[0] = " + measuredWidth[0] + ","
            );

            start += measureCount;
            lineCounts++;
        }

        // TODO: 18-1-18 lastLineTextWidth 
        TagLog.i(TAG, "onDraw() : " + " lastLineTextWidth = " + lastLineTextWidth + ",");

        // TODO: 18-1-18 lineCounts 
        TagLog.i(TAG, "onDraw() : " + " lineCounts = " + lineCounts + ",");

        // TODO: 18-1-18 getTextBounds get Rect
        int bottomCenterHeight = getBottom();
        TagLog.i(TAG, "onDraw() : " + " bottomCenterHeight = " + bottomCenterHeight + ",");

        // TODO: 18-1-18 getBaseLine y
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        bottomCenterHeight -= (fontMetrics.bottom - fontMetrics.top) / 2;

        // TODO: 18-1-18 get Line Space
        float fontSpacing = textPaint.getFontSpacing();
        TagLog.i(TAG, "onDraw() : " + " fontSpacing = " + fontSpacing + ",");


        // TODO: 18-1-18 try draw debug line
        canvas.drawLine(getLeft(), bottomCenterHeight, getRight(), bottomCenterHeight, mDebugDrawPaint);

        canvas.drawLine(lastLineTextWidth, getTop(), lastLineTextWidth, getBottom(), mDebugDrawPaint);


        float lastLineLeftTopHeight = bottomCenterHeight - fontSpacing / 2f;
        canvas.drawLine(getLeft(), lastLineLeftTopHeight, getRight(), lastLineLeftTopHeight, mDebugDrawPaint);

        float lastLineLeftBottomHeight = bottomCenterHeight + fontSpacing / 2f;
        canvas.drawLine(getLeft(), lastLineLeftBottomHeight, getRight(), lastLineLeftBottomHeight, mDebugDrawPaint);

//        getLayoutParams().height +=
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        TagLog.i(TAG, "onMeasure() : " + " heightMode = " + heightMode + ","
                + " heightSize = " + heightSize + ",");

        // TODO: 18-1-18 getWidth


        /*// get Text height and width
        StaticLayout staticLayout = new StaticLayout(getText(), getPaint(),
                getWidth(), // 1080, // TODO: 18-1-18 get width
                Layout.Alignment.ALIGN_NORMAL, 1f, 0, true);
        int lineCount = staticLayout.getLineCount();
        int staticLayoutWidth = staticLayout.getWidth();
        int staticLayoutHeight = staticLayout.getHeight();

        TagLog.i(TAG, "onMeasure() : " + " getWidth() = " + getWidth() + ",");

        TagLog.i(TAG, "onMeasure() : " + " lineCount = " + lineCount + ",");
        TagLog.i(TAG, "onMeasure() : " + " staticLayoutWidth = " + staticLayoutWidth + ",");
        TagLog.i(TAG, "onMeasure() : " + " staticLayoutHeight = " + staticLayoutHeight + ",");


        setMeasuredDimension(widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(staticLayoutHeight + 100, MeasureSpec.EXACTLY));
*/
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        TagLog.i(TAG, "onSizeChanged() : " + " height = " + h + ",");
        TagLog.i(TAG, "onSizeChanged() : " + " width = " + w + ",");
        this.mWidth = w;
        this.mHeight = h;
        ;
    }
}
