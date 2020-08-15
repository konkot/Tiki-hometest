package com.tt.hometest.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.tt.hometest.MainActivity;
import com.tt.hometest.R;

public class SearchBar extends FrameLayout {
    public SearchBar(@NonNull Context context) {
        super(context);
        init();
    }

    public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    int marginRight, padding;
    int editTextMarginRight;
    //int iconPaddingLeft, iconPaddingTop, iconPaddingBottom;
    NinePatchDrawable background;
    //Drawable searchIcon;
    EditText editText;

    private void init(){
        background = (NinePatchDrawable) ContextCompat.getDrawable(getContext(), R.drawable.rounded_bar2);
        //searchIcon = ContextCompat.getDrawable(getContext(), R.drawable.baseline_search_black_24);
        setWillNotDraw(false);
        padding = getResources().getDimensionPixelSize(R.dimen.search_bar_padding);
        /*iconPaddingLeft = getResources().getDimensionPixelSize(R.dimen.search_icon_padding_left);
        iconPaddingTop = getResources().getDimensionPixelSize(R.dimen.search_icon_padding_top);
        iconPaddingBottom = getResources().getDimensionPixelSize(R.dimen.search_icon_padding_bottom);*/
    }

    public void setEditTextCollapsed(boolean collapsed) {
        if (editTextMarginRight == 0)
            editTextMarginRight = getResources().getDimensionPixelSize(R.dimen.search_bar_margin_right);
        if (editText == null)
            editText = findViewById(R.id.editText);
        FrameLayout.LayoutParams layoutParams = (LayoutParams) editText.getLayoutParams();
        int r = collapsed ? editTextMarginRight : 0;
        if (layoutParams.getMarginEnd() != r) {
            layoutParams.setMarginEnd(r);
            editText.setLayoutParams(layoutParams);
            MainActivity.log(this, "edittext collapsed " + collapsed);
        }
    }

    public void setMarginRight(int r) {
        if (marginRight == r)
            return;
        marginRight = r;
        invalidate();
    }

    private Rect bounds = new Rect();
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.getClipBounds(bounds);
        bounds.set(bounds.left + padding, bounds.top + padding, bounds.right - marginRight - padding, bounds.bottom - padding);
        background.setBounds(bounds);
        background.draw(canvas);
        /*bounds.set(bounds.left + iconPaddingLeft, bounds.top + iconPaddingTop,
                bounds.left + iconPaddingLeft + bounds.height() - iconPaddingTop - iconPaddingBottom, bounds.bottom - iconPaddingBottom);
        searchIcon.setBounds(bounds);
        searchIcon.draw(canvas);*/
        super.onDraw(canvas);
    }
}
