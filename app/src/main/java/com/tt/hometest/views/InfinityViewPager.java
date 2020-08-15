package com.tt.hometest.views;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.tt.hometest.Adapter;
import com.tt.hometest.AdapterBanner;

public class InfinityViewPager {

    private static final int SLIDING_INTERVAL = 1500;
    private ViewPager2.OnPageChangeCallback viewPagerCallback;
    private Runnable slider;
    private ViewPager2 viewPager;


    public InfinityViewPager(ViewPager2 viewPager) {
        this.viewPager = viewPager;
    }

    public void enableInifityScroll(boolean autoSlide) {
        //AdapterBanner adapter = (AdapterBanner) viewPager.getAdapter();
        if (viewPagerCallback == null) {
            viewPagerCallback = new ViewPager2.OnPageChangeCallback() {

                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    AdapterBanner adapter = (AdapterBanner) viewPager.getAdapter();
                    super.onPageScrollStateChanged(state);
                    if (state == ViewPager2.SCROLL_STATE_IDLE) {
                        if (viewPager.getCurrentItem() == adapter.getItemCount() - 1) {
                            viewPager.setCurrentItem(adapter.getMiddlePostion() - 1, false);
                        }
                        else if (viewPager.getCurrentItem() == 0) {
                            viewPager.setCurrentItem(adapter.getMiddlePostion(), false);
                        }
                        setAutoSlideCallback(true);

                    } else if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                        setAutoSlideCallback(false);
                    }
                }
            };
        }
        viewPager.unregisterOnPageChangeCallback(viewPagerCallback);
        viewPager.registerOnPageChangeCallback(viewPagerCallback);

    }

    public void disableInifityScroll() {
        if (viewPagerCallback != null)
            viewPager.unregisterOnPageChangeCallback(viewPagerCallback);
    }

    public void setAutoSlideCallback(boolean enable) {
        if (slider == null)
            slider = new Runnable() {
                @Override
                public void run() {
                    RecyclerView.Adapter adapter = viewPager.getAdapter();
                    if (adapter != null && adapter.getItemCount() > 0)
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                }
            };

        if (enable) {
            viewPager.removeCallbacks(slider);
            viewPager.postDelayed(slider, SLIDING_INTERVAL);
        } else {
            viewPager.removeCallbacks(slider);
        }
    }



    public void stop(){
        setAutoSlideCallback(false);
    }

    public void start(){
        setAutoSlideCallback(true);
    }





}
