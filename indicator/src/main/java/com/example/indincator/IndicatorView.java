package com.example.indincator;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Copyright@ AsianTech.Inc
 * Created by ly.ho.sumiu on 16/02/2017.
 */

public class IndicatorView extends View implements ViewPager.OnPageChangeListener, IndicatorInterface {
    private static final String TAG = IndicatorView.class.getSimpleName();

    private static final long DEFAULT_ANIMATE_DURATION = 200;

    private static final int DEFAULT_RADIUS_SELECTED = 20;

    private static final int DEFAULT_RADIUS_UNSELECTED = 15;
    private static final int DEFAULT_DISTANCE = 40;

    private ViewPager mViewPager;

    private Dot[] mDots;

    private long mAnimateDuration = DEFAULT_ANIMATE_DURATION;

    private int mRadiusSelected = DEFAULT_RADIUS_SELECTED;

    private int mRadiusUnselected = DEFAULT_RADIUS_UNSELECTED;

    private int mDistance = DEFAULT_DISTANCE;

    private int mColorSelected;

    private int mColorUnselected;

    private int mCurrentPosition;

    private int mBeforePosition;

    private ValueAnimator mAnimatorZoomIn;

    private ValueAnimator mAnimatorZoomOut;

    public IndicatorView(Context context) {
        super(context);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeAttributeSet(context, attrs);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // show khi cac thanh phan view da duoc tinh toan xong, thuc hien ham onLayout
    // set cac thuoc tinh cho dots
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Log.d(TAG, "onLayout: ");
        // toa do y cac dots
        float yCenter = getHeight() / 2;
        // Khoang cach tam 2 dots unSelected
        int d = mDistance + 2 * mRadiusUnselected;
        // Toa x do cua dot dau tien
        float firstXCenter = (getWidth() / 2) - ((mDots.length - 1) * d / 2);
        for (int i = 0; i < mDots.length; i++) {
            mDots[i].setCenter(i == 0 ? firstXCenter : firstXCenter + d * i, yCenter);
            mDots[i].setCurrentRadius(i == mCurrentPosition ? mRadiusSelected : mRadiusUnselected);
            mDots[i].setColor(i == mCurrentPosition ? mColorSelected : mColorUnselected);
            mDots[i].setAlpha(i == mCurrentPosition ? 255 : mRadiusUnselected * 255 / mRadiusSelected);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int desiredHeight = 2 * mRadiusSelected;

        int width;
        int height;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = widthSize;
        } else {
            width = 0;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Dot dot : mDots) {
            dot.draw(canvas);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        runAnimation(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void setViewPager(ViewPager viewPager) throws PagesException {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        initDot(viewPager.getAdapter().getCount());
        onPageSelected(0);
    }

    @Override
    public void setAnimateDuration(long duration) {
        mAnimateDuration = duration;
    }

    @Override
    public void setRadiusSelected(int radius) {
        mRadiusSelected = radius;
    }

    @Override
    public void setRadiusUnselected(int radius) {
        mRadiusUnselected = radius;
    }

    @Override
    public void setDistanceDot(int distance) {
        mDistance = distance;
    }

    private void setTypeAttributeSet(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView);

        mRadiusSelected = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_sumiu_radius_selected, DEFAULT_RADIUS_SELECTED);

        mRadiusUnselected = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_sumiu_radius_unselected, DEFAULT_RADIUS_UNSELECTED);

        mDistance = typedArray.getInt(R.styleable.IndicatorView_sumiu_distance, DEFAULT_DISTANCE);

        mColorSelected = typedArray.getColor(R.styleable.IndicatorView_sumiu_color_selected, Color.parseColor("#ffffff"));

        mColorUnselected = typedArray.getColor(R.styleable.IndicatorView_sumiu_color_unselected, Color.parseColor("#ffffff"));

        typedArray.recycle();
    }

    private void initDot(int count) throws PagesException {
        if (count < 2) throw new PagesException();
        mDots = new Dot[count];
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new Dot();
        }
    }

    private void runAnimation(int position) {
        Log.d(TAG, "runAnimation: ");
        mBeforePosition = mCurrentPosition;
        mCurrentPosition = position;

        if (mBeforePosition == mCurrentPosition) {
            mBeforePosition = mCurrentPosition + 1;
        }

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(mAnimateDuration);

        mAnimatorZoomIn = ValueAnimator.ofInt(mRadiusUnselected, mRadiusSelected);
        mAnimatorZoomIn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            int positionPerform = mCurrentPosition;

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int newRadius = (int) valueAnimator.getAnimatedValue();
                changeNewRadius(positionPerform, newRadius);
            }
        });

        mAnimatorZoomOut = ValueAnimator.ofInt(mRadiusSelected, mRadiusUnselected);
        mAnimatorZoomOut.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            int positionPerform = mBeforePosition;

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int newRadius = (int) valueAnimator.getAnimatedValue();
                changeNewRadius(positionPerform, newRadius);
            }
        });

        animatorSet.play(mAnimatorZoomIn).with(mAnimatorZoomOut);
        animatorSet.start();

    }

    private void changeNewRadius(int positionPerform, int newRadius) {
        if (mDots[positionPerform].getCurrentRadius() != newRadius) {
            mDots[positionPerform].setCurrentRadius(newRadius);
            mDots[positionPerform].setAlpha(newRadius * 255 / mRadiusSelected);
            invalidate();
        }
    }
}
