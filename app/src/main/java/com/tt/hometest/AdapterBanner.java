package com.tt.hometest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.tt.hometest.views.MyImageView;
import com.tt.hometest.model.BannerItems;

import java.util.List;

public class AdapterBanner extends Adapter<AdapterBanner.ViewHolder> {

    List<BannerItems.Item> data;

    public AdapterBanner(Context context, BannerItems data, OnAllViewLoaded onLoadedCallback) {
        super(context, data, onLoadedCallback);
        this.data = data.data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_banner, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        position = position % data.size();
        BannerItems.Item item = data.get(position);
        loadImageAsBitmap(holder.imageView, item.getMobileUrl(), true);
        holder.itemView.setOnClickListener(v -> MainActivity.showMsgDialog(context, item.getUrl()));
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        if (data.size() == 1)
            return 1;
        //return Integer.MAX_VALUE - Integer.MAX_VALUE % getRealItemCount();
        return getRealItemCount()*4;
    }

    public int getRealItemCount() {
        return data == null ? 0 : data.size();
    }

    public int getMiddlePostion(){
        //int m = getItemCount()/2;
        //return m - m % getRealItemCount();
        return getItemCount()/2;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public MyImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

}
