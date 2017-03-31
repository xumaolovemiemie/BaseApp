package com.wisdom.rxjava.widget.loading;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import com.wisdom.rxjava.R;

/**
 * Created by wisdom on 17/3/31.
 */

public class WisdomView extends View {
    private Paint mPaint;
    private float radius;
    private float rotationRadius;
    private int systemWidth;
    private ObjectAnimator animator;

    public WisdomView(Context context) {
        super(context);
        init(context);
    }

    public WisdomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WisdomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureSize(widthMeasureSpec);
        int height = measureSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureSize(int measureSpec) {
        int result;
        int size = MeasureSpec.getSize(measureSpec);
        int mode = MeasureSpec.getMode(measureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            result = size;
        } else {
            result = systemWidth / 7;
            if (mode == MeasureSpec.EXACTLY) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    private void init(Context context) {
        getSystemInfo(context);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.FILL);

        radius = ((float) systemWidth) / 80;
        rotationRadius = 0f;
    }

    private void getSystemInfo(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        systemWidth = manager.getDefaultDisplay().getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(rotationRadius * 360);
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    mPaint.setColor(getResources().getColor(R.color.yellow));
                    break;
                case 1:
                    mPaint.setColor(getResources().getColor(R.color.red));
                    break;
                case 2:
                    mPaint.setColor(getResources().getColor(R.color.blue));
                    break;
                case 3:
                    mPaint.setColor(getResources().getColor(R.color.purple));
                    break;
            }
            canvas.rotate(-90);
            RectF rectF;
            if (rotationRadius <= 0.5f) {
                rectF = new RectF(-radius, 2 * radius * (1 - rotationRadius), radius, 2 * radius * (1 - rotationRadius) + 2 * radius);
            } else {
                rectF = new RectF(-radius, 2 * radius * rotationRadius, radius, 2 * radius * rotationRadius + 2 * radius);
            }
            canvas.drawOval(rectF, mPaint);
        }
    }

    public void start() {
        animator = ObjectAnimator.ofFloat(this, "rotationRadius", 0, 1f);
        animator.setDuration(800);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    public void cancel() {
        animator.cancel();
    }

    public float getRotationRadius() {
        return rotationRadius;
    }

    public void setRotationRadius(float rotationRadius) {
        this.rotationRadius = rotationRadius;
        invalidate();
    }
}
