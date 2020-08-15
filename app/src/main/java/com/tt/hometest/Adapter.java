package com.tt.hometest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.tt.hometest.views.MyImageView;
import com.tt.hometest.model.Model;

public abstract class Adapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    Context context;
    LayoutInflater inflater;

    public interface OnAllViewLoaded {
        void onAllViewLoaded();
        void onAllViewFailedToLoad();
    }

    OnAllViewLoaded onLoadedCallback;
    protected int loadingItem, loadedItem, failedItem;
    protected boolean firstLoad = true;

    public Adapter(Context context, Model data, OnAllViewLoaded onLoadedCallback) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.onLoadedCallback = onLoadedCallback;

    }

    protected void checkLoaded() {
        MainActivity.log(this, "check loaded " + loadedItem + " " + failedItem + " " + loadingItem);
        if (failedItem == loadingItem) {
            firstLoad = false;
            onLoadedCallback.onAllViewFailedToLoad();
        }
        else if (loadedItem + failedItem == loadingItem) {
            firstLoad = false;
            onLoadedCallback.onAllViewLoaded();
        }
    }

    protected void loadImage(MyImageView view, String url) {
        MainActivity.log(this, "load first " + Adapter.this.getClass().getSimpleName() + " " + firstLoad);
        if (url == null) {
            loadedItem++;
            loadedItem++;
            Glide.with(context).clear(view);
            view.hideIcon();
            checkLoaded();
            return;
        }
        view.showIcon();
        if (firstLoad) {
            loadingItem++;
            MainActivity.log(this, "load start " + Adapter.this.getClass().getSimpleName() );
            Glide.with(context).load(url).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    failedItem++;
                    MainActivity.log(this, "load failed " + Adapter.this.getClass().getSimpleName() + " " + failedItem + " / " + loadingItem );
                    checkLoaded();
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    loadedItem++;
                    MainActivity.log(this, " load ok " + Adapter.this.getClass().getSimpleName() + " "  + loadedItem + " / " + loadingItem);
                    checkLoaded();
                    return false;
                }
            }).into(view);

        } else
            Glide.with(context).load(url).into(view);
    }

    protected void loadImageAsBitmap(final MyImageView view, String url, final boolean roundCorners) {
        if (url == null) {
            loadedItem++;
            loadedItem++;
            Glide.with(context).clear(view);
            view.hideIcon();
            checkLoaded();
            return;
        }
        view.showIcon();
        if (firstLoad) {
            loadingItem++;
            Glide.with(context).asBitmap().load(url).listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    failedItem++;
                    MainActivity.log(this, "failed " + Adapter.this.getClass().getSimpleName() + " " + failedItem + " / " + loadingItem );
                    checkLoaded();
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    loadedItem++;
                    MainActivity.log(this, "load ok " + Adapter.this.getClass().getSimpleName() + " "  + loadedItem + " / " + loadingItem);
                    checkLoaded();
                    return false;
                }
            }).into(new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    view.setImageBitmap(roundCorners ? getRoundedCornerBitmap(resource) : resource);
                    //view.hideIcon();
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {

                }
            });
            MainActivity.log(this, "load item count " + Adapter.this.getClass().getSimpleName() + " "+ loadedItem);
        } else
            Glide.with(context).asBitmap().load(url).into(new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    view.setImageBitmap(getRoundedCornerBitmap(resource));
                    //view.hideIcon();
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {

                }
            });
    }

    private Drawable mask; Paint paint; Rect bound = new Rect();

    public Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        if (mask == null)
            mask = ContextCompat.getDrawable(context, R.drawable.rounded_bar2);
        if (paint == null) {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        }
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(output);
        tempCanvas.getClipBounds(bound);
        mask.setBounds(bound);
        mask.draw(tempCanvas);
        tempCanvas.drawBitmap(bitmap, 0, 0, paint);
        return output;




    }


}
