package com.tt.hometest.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.core.content.ContextCompat;

import com.tt.hometest.R;

public class ProgressBar extends androidx.appcompat.widget.AppCompatTextView {
    public ProgressBar(Context context) {
        super(context);
    }

    public ProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int frontColor = ContextCompat.getColor(getContext(), R.color.pinkDark),
            backColor = ContextCompat.getColor(getContext(), R.color.pinkLight);
    private float progress;
    private Rect bound = new Rect();
    private RectF boundF = new RectF();
    private Paint paint;

    public void setProgress(float progress) {
        this.progress = Math.min(1, progress);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (paint == null) {
            paint = new Paint();
            paint.setAntiAlias(true);
        }

        canvas.getClipBounds(bound);
        boundF.set(bound);
        paint.setColor(backColor);
        canvas.drawRoundRect(boundF, 10000, 10000, paint);
        boundF.right = Math.max(boundF.left + boundF.height(), boundF.left + (boundF.width() * progress));
        paint.setColor(frontColor);
        canvas.drawRoundRect(boundF, 10000, 10000, paint);

        super.onDraw(canvas);
    }
}
