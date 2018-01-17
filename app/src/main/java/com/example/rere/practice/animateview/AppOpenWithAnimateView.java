package com.example.rere.practice.animateview;

import com.example.rere.practice.R;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * app open with animate
 *
 * Created by rere on 18-1-5.
 */
public class AppOpenWithAnimateView extends RelativeLayout {

    private Context mContext;
    private View mViewMoving;
    private int mWidth;
    private int mHeight;
    private View mCheckedContainer;

    private static final int CONSTANTS_ANIMATION_DURATION = 800;

    public AppOpenWithAnimateView(Context context) {
        super(context);
        init(context);
    }

    public AppOpenWithAnimateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AppOpenWithAnimateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public AppOpenWithAnimateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        findViews();
    }

    private void findViews() {
        // used code
        this.setBackgroundResource(R.mipmap.bg);// todo bg rename

        // check view
        mCheckedContainer = new View(mContext);
        mCheckedContainer.setBackgroundResource(R.mipmap.bg_checked);
        this.addView(mCheckedContainer,
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        // TODO: 18-1-8 set init check property

        // moving view
        mViewMoving = new View(mContext);
        mViewMoving.setBackgroundResource(R.mipmap.inside_home);
        resetMovingView();
        this.addView(mViewMoving,
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));// TODO: 18-1-8 size
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
    }

    private void resetMovingView() {
        // 18-1-8 reset moving view size
        LayoutParams params = new LayoutParams((int) (0.9 * mWidth), (int) (0.7671 * mHeight));
        params.leftMargin = (int) (0.045833334f * mWidth);
        params.topMargin = (int) (0.11646587f * mHeight);
        mViewMoving.setLayoutParams(params);

        mViewMoving.setAlpha(0);
        mViewMoving.setScaleX(0);
        mViewMoving.setScaleY(0);
    }

    public void startAnimate() {
        resetMovingView();

        mViewMoving.setPivotX((int) (0.22916667f * mWidth));
        mViewMoving.setPivotY((int) (0.11445783f * mHeight));
        mViewMoving.animate()
                .alpha(1)
                .scaleX(1)
                .scaleY(1)
                .setDuration(CONSTANTS_ANIMATION_DURATION)
                .start();
    }

    public void resetAnimate() {
        resetMovingView();
    }

    public void rollbackAnimate() {
        mViewMoving.setPivotX((int) (0.22916667f * mWidth));
        mViewMoving.setPivotY((int) (0.11445783f * mHeight));
        mViewMoving.animate()
                .alpha(0)
                .scaleX(0)
                .scaleY(0)
                .start();
    }

    public void setViewMovingRes(int resId) {
        if (null != mViewMoving) {
            mViewMoving.setBackgroundResource(resId);
        }
    }

}
