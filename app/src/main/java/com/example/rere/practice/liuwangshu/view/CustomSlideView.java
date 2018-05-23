package com.example.rere.practice.liuwangshu.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by rere on 18-5-2.
 */

public class CustomSlideView extends View {

    private int mLastX;
    private int mLastY;
    private Scroller mScroller;

    public CustomSlideView(Context context) {
        super(context);
        init(context);
    }

    public CustomSlideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomSlideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                int offsetX = x - mLastX;
                int offsetY = y - mLastY;
                slideInMotionMove(offsetX, offsetY);
                break;
        }

        return true;
    }

    private void slideInMotionMove(int offsetX, int offsetY) {
        // 1. layout
//                layout(getLeft() + offsetX, getTop() + offsetY,
//                        getRight() + offsetX, getBottom() + offsetY);

        // 2. offset*
//                offsetLeftAndRight(offsetX);
//                offsetTopAndBottom(offsetY);

        // 1和2效果类似：不能超出父布局；存在层级关系

        // 3. layoutParams
//        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
//        layoutParams.leftMargin += offsetX;
//        layoutParams.topMargin  += offsetY;
//        setLayoutParams(layoutParams);
        // 3 ： 能超越父布局，父布局的大小会跟着变化；也存在层级关系

        // 4. scrollBy
        ((View) getParent()).scrollBy(-offsetX, -offsetY);
        // 4. 实际上是parent的滚动，所以会影响其他view
    }

    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        //1000秒内滑向destX
        mScroller.startScroll(scrollX, 0, delta, 0, 2000);
        invalidate();
    }

    // invalidate() call computeScroll()
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(-mScroller.getCurrX(), -mScroller.getCurrY());
            //通过不断的重绘不断的调用computeScroll方法
            invalidate();
        }

    }
}
