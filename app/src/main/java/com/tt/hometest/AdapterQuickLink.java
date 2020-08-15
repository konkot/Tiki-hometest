package com.tt.hometest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tt.hometest.views.MyImageView;
import com.tt.hometest.model.QuickLinkItems;

public class AdapterQuickLink extends Adapter<AdapterQuickLink.ViewHolder> {

    QuickLinkItems data;
    int rowCount;
    int columnCount;


    public AdapterQuickLink(Context context, QuickLinkItems data, OnAllViewLoaded onLoadedCallback) {
        super(context, data, onLoadedCallback);
        this.data = data;
        rowCount = data.getRowCount();
        if (rowCount == 0) {
            onLoadedCallback.onAllViewFailedToLoad();
        } else {
            for (int i=0; i<rowCount; ++i) {
                int s = data.getRow(i).size();
                columnCount = Math.max(columnCount, s);
            }
            if (columnCount == 0)
                onLoadedCallback.onAllViewFailedToLoad();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) inflater.inflate(R.layout.item_quick_link, parent, false);
        if (rowCount == 0) {
            onLoadedCallback.onAllViewFailedToLoad();
            return null;
        }
        MyImageView[] imageViews = new MyImageView[rowCount];
        TextView[] textViews = new TextView[rowCount];
        for (int i=0; i<rowCount; ++i) {
            MyImageView icon = (MyImageView) inflater.inflate(R.layout.item_quick_link_icon, v, false);
            TextView text = (TextView) inflater.inflate(R.layout.item_quick_link_text, v, false);
            v.addView(icon);
            v.addView(text);
            imageViews[i] = icon;
            textViews[i] = text;
        }
        ViewHolder holder = new ViewHolder(v, imageViews, textViews);

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder == null)
            return;
        for (int i=0; i<rowCount; ++i) {
            if (position < data.getRow(i).size()) {
                QuickLinkItems.Item item = data.getRow(i).get(position);
                loadImage(holder.imageViews[i], item.getImageUrl());
                holder.textViews[i].setText(item.getTitle());
                holder.imageViews[i].setOnClickListener(v -> MainActivity.showMsgDialog(context, item.getUrl()));
                holder.textViews[i].setOnClickListener(v -> MainActivity.showMsgDialog(context, item.getUrl()));
            } else {
                loadImage(holder.imageViews[i], null);
                holder.textViews[i].setText(null);
            }
        }

    }

    @Override
    public int getItemCount() {
        return columnCount;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public MyImageView[] imageViews;
        public TextView[] textViews;

        public ViewHolder(@NonNull View itemView, MyImageView[] imageViews, TextView[] textViews) {
            super(itemView);
            this.imageViews = imageViews;
            this.textViews = textViews;
        }
    }
}
