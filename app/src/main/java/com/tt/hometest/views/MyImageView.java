package com.tt.hometest.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.tt.hometest.R;

public class MyImageView extends androidx.appcompat.widget.AppCompatImageView {
    public MyImageView(Context context) {
        super(context);
        init();
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Drawable icon;
    private int iconSize;
    private Rect bound = new Rect();
    private boolean showIcon;

    private void init() {
        icon = ContextCompat.getDrawable(getContext(), R.drawable.ic_photo_black_32dp);
        iconSize = getResources().getDimensionPixelSize(R.dimen.image_icon_size);
    }

    public void showIcon() {
        showIcon = true;
        invalidate();
    }

    public void hideIcon() {
        showIcon = false;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null && showIcon) {
            canvas.getClipBounds(bound);
            int paddingHorz = (bound.width() - iconSize) / 2,
                    paddingVert = (bound.height() - iconSize) / 2;
            bound.set(bound.left + paddingHorz, bound.top + paddingVert, bound.right - paddingHorz, bound.bottom - paddingVert);
            icon.setBounds(bound);
            icon.draw(canvas);
        }
        else
            super.onDraw(canvas);
    }
}
