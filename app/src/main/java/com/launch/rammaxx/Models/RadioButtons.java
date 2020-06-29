package com.launch.rammaxx.Models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class RadioButtons extends View {
    //the number of slice
    private int mSlices = 4;

    //the angle of each slice
    private int degreeStep = 360 / mSlices;

    private int quarterDegreeMinus = -90;

    private float mOuterRadius;
    private float mInnerRadius;

    //using radius square to prevent square root calculation
    private float outerRadiusSquare;
    private float innerRadiusSquare;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mSliceOval = new RectF();

    private static final double quarterCircle = Math.PI / 2;

    private float innerRadiusRatio = 0.3F;

    //color for your slice
    private int[] colors = new int[]{Color.GREEN, Color.GRAY, Color.BLUE, Color.CYAN, Color.DKGRAY, Color.RED};

    private int mCenterX;
    private int mCenterY;

    private OnSliceClickListener mOnSliceClickListener;
    private int mTouchSlop;

    private boolean mPressed;
    private float mLatestDownX;
    private float mLatestDownY;

    private Canvas mCanvas;

    public int getTouchedSlice() {
        return touchedSlice;
    }

    private int touchedSlice = 5;

    public interface OnSliceClickListener{
        void onSlickClick(int slicePosition);
    }

    public RadioButtons(Context context){
        this(context, null);
    }

    public RadioButtons(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public RadioButtons(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);

        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = viewConfiguration.getScaledTouchSlop();

        mPaint.setStrokeWidth(10);
    }

    public void setOnSliceClickListener(OnSliceClickListener onSliceClickListener){
        mOnSliceClickListener = onSliceClickListener;
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh){
        mCenterX = w / 2;
        mCenterY = h / 2;

        mOuterRadius = mCenterX > mCenterY ? mCenterY : mCenterX;
        mInnerRadius = mOuterRadius * innerRadiusRatio;

        outerRadiusSquare = mOuterRadius * mOuterRadius;
        innerRadiusSquare = mInnerRadius * mInnerRadius;

        mSliceOval.left = mCenterX - mOuterRadius;
        mSliceOval.right = mCenterX + mOuterRadius;
        mSliceOval.top = mCenterY - mOuterRadius;
        mSliceOval.bottom = mCenterY + mOuterRadius;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float currX = event.getX();
        float currY = event.getY();

        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                mLatestDownX = currX;
                mLatestDownY = currY;

                mPressed = true;
                break;
            case MotionEvent.ACTION_MOVE:


                if(Math.abs(currX - mLatestDownX) > mTouchSlop || Math.abs(currY - mLatestDownY) > mTouchSlop) mPressed = false;
                break;
            case MotionEvent.ACTION_UP:

                if(mPressed){
                    int dx = (int) currX - mCenterX;
                    int dy = (int) currY - mCenterY;
                    int distanceSquare = dx * dx + dy * dy;

                    //if the distance between touchpoint and centerpoint is smaller than outerRadius and longer than innerRadius, then we're in the clickable area
                    if(distanceSquare > innerRadiusSquare && distanceSquare < outerRadiusSquare){

                        //get the angle to detect which slice is currently being click
                        double angle = Math.atan2(dy, dx);

                        if(angle >= -quarterCircle && angle < 0){
                            angle += quarterCircle;
                        }else if(angle >= -Math.PI && angle < -quarterCircle){
                            angle += Math.PI + Math.PI + quarterCircle;
                        }else if(angle >= 0 && angle < Math.PI){
                            angle += quarterCircle;
                        }

                        double rawSliceIndex = angle / (Math.PI * 2) * mSlices;

                        reDraw((int) rawSliceIndex);

                        if(mOnSliceClickListener != null){
                            mOnSliceClickListener.onSlickClick((int) rawSliceIndex);
                        }

                    }
                }
                break;
        }

        return true;
    }

    @Override
    public void onDraw(Canvas canvas){
        int startAngle = quarterDegreeMinus;
        mCanvas = canvas;

        //draw slice
        for(int i = 0; i < mSlices; i++){
            mPaint.setStyle(Paint.Style.FILL);
            //mPaint.setColor(colors[i % colors.length]);
            mPaint.setColor(Color.parseColor("#106E1F"));
            canvas.drawArc(mSliceOval, startAngle, degreeStep, true, mPaint);

            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.WHITE);
            canvas.drawArc(mSliceOval, startAngle, degreeStep, true, mPaint);

            startAngle += degreeStep;
        }

        // Draw touched slice
        if(touchedSlice < mSlices){
            startAngle = quarterDegreeMinus + (touchedSlice)*degreeStep;
            mPaint.setStyle(Paint.Style.FILL);
            //mPaint.setColor(colors[i % colors.length]);
            mPaint.setColor(Color.parseColor("#5ACC2C"));
            canvas.drawArc(mSliceOval, startAngle, degreeStep, true, mPaint);

            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.WHITE);
            canvas.drawArc(mSliceOval, startAngle, degreeStep, true, mPaint);
        }

        //draw center circle
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(mCenterX, mCenterY, mInnerRadius, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCenterX, mCenterY, mInnerRadius, mPaint);
    }

    public void reDraw(int slice)
    {
        touchedSlice = slice;
        invalidate();
        /*int startAngle = quarterDegreeMinus;
        for(int i = 0; i < mSlices; i++) {
            mPaint.setStyle(Paint.Style.FILL);
            //mPaint.setColor(colors[i % colors.length]);
            mPaint.setColor(Color.GREEN);
            canvas.drawArc(mSliceOval, startAngle, degreeStep, true, mPaint);

            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.WHITE);
            canvas.drawArc(mSliceOval, startAngle, degreeStep, true, mPaint);

            startAngle += degreeStep;
        }

        startAngle += quarterDegreeMinus + slice*degreeStep;
        mPaint.setStyle(Paint.Style.FILL);
        //mPaint.setColor(colors[i % colors.length]);
        mPaint.setColor(Color.YELLOW);
        canvas.drawArc(mSliceOval, startAngle, degreeStep, true, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        canvas.drawArc(mSliceOval, startAngle, degreeStep, true, mPaint);*/


    }
}

