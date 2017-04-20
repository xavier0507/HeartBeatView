package com.xy.heartbeatimageview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.xy.heartbeatimageview.R;

/**
 * Created by Xavier Yin on 4/20/17.
 */

public class HeartBeatImageView extends View {
    private static final int ANIM_COUNTER_MAX = 20;
    private static final float DEFAUT_ANIM_MOVE_FACTOR = 1;
    private static final int DEFAULT_ANIM_COUNTER = 0;
    private static final int DEFAULT_STEP = 1;
    private static final int DEFAULT_COLOR = 0xFFFF4081;

    private Paint redCirclePaint;
    private float animMoveFactor = DEFAUT_ANIM_MOVE_FACTOR;
    private int animCounter = DEFAULT_ANIM_COUNTER;
    private int step = DEFAULT_STEP;
    private int heartColor = DEFAULT_COLOR;

    public HeartBeatImageView(Context context) {
        this(context, null);
    }

    public HeartBeatImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartBeatImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initParams(context, attrs);
        this.initPaint();
    }

    private void initParams(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeartBeatImageView);
        this.animMoveFactor = typedArray.getFloat(R.styleable.HeartBeatImageView_animMoveFactor, DEFAUT_ANIM_MOVE_FACTOR);
        this.animCounter = typedArray.getInt(R.styleable.HeartBeatImageView_animCounter, DEFAULT_ANIM_COUNTER);
        this.heartColor = typedArray.getColor(R.styleable.HeartBeatImageView_heartColor, DEFAULT_COLOR);
    }

    private void initPaint() {
        this.redCirclePaint = new Paint();
        this.redCirclePaint.setAntiAlias(true);
        this.redCirclePaint.setColor(this.heartColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.drawHeart(canvas);
        this.invalidate();
    }

    private void drawHeart(Canvas canvas) {
        this.animCounter = this.animCounter + this.step;

        int centerX = canvas.getWidth() / 2;
        int centerY = canvas.getHeight() / 2;
        float radius = this.getCircleRadius(canvas.getWidth() / 6, this.animCounter, this.animMoveFactor);

        canvas.rotate(45, centerX, centerY);

        canvas.drawRect(centerX - radius, centerY - radius, centerX + radius, centerY + radius, this.redCirclePaint);
        canvas.drawCircle(centerX - radius, centerY, radius, this.redCirclePaint);
        canvas.drawCircle(centerX, centerY - radius, radius, this.redCirclePaint);

        if (this.animCounter >= ANIM_COUNTER_MAX) {
            this.step = -1;
        } else if (this.animCounter <= 0) {
            this.step = 1;
        }
    }

    private float getCircleRadius(int radius, int animCounter, double animFactor) {
        return (float) (radius + animCounter * animFactor);
    }
}
