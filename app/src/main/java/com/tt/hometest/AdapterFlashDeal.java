package com.tt.hometest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tt.hometest.views.MyImageView;
import com.tt.hometest.views.ProgressBar;
import com.tt.hometest.model.FlashDealItems;

public class AdapterFlashDeal extends Adapter<RecyclerView.ViewHolder> {

    FlashDealItems data;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public AdapterFlashDeal(Context context, FlashDealItems data,  OnAllViewLoaded onLoadedCallback) {
        super(context, data, onLoadedCallback);
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == TYPE_ITEM) {
            View v = inflater.inflate(R.layout.item_flash_deal, parent, false);
            holder = new ViewHolderItem(v);
        } else  {
            View v = inflater.inflate(R.layout.item_flash_deal_footer, parent, false);
            holder = new ViewHolderFooter(v);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderItem) {
            ViewHolderItem holderItem = (ViewHolderItem) holder;
            loadImage(holderItem.imageView, data.getThumbnailUrl(position));
            holderItem.priceText.setText(String.format("%,d", data.getSpecialPrice(position)).replace(",",".") +
                    context.getString(R.string.currency));
            int ordered = data.getQtyOrdered(position);
            int qty = data.getQty(position);
            String s;
            if (ordered <= 1)
                s = context.getString(R.string.just_sale);
            else
                s = context.getString(R.string.ordered) + ordered;
            holderItem.progressBar.setText(s);
            float p = ordered/(float)qty;
            holderItem.progressBar.setProgress(p);
            holderItem.fireIcon.setVisibility(p > 0.5f ? View.VISIBLE : View.INVISIBLE);
            holderItem.discountText.setText(String.format("-%d%%", data.getDiscountPercent(position)));
            holderItem.itemView.setOnClickListener(v -> MainActivity.showMsgDialog(context, data.getUrl(position)));

        }

        else {
            ViewHolderFooter holderFooter = (ViewHolderFooter) holder;
            holderFooter.itemView.setOnClickListener(v -> MainActivity.showMsgDialog(context, context.getString(R.string.view_more)));
            // TODO
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position < data.getItemCount() ? TYPE_ITEM : TYPE_FOOTER);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : (data.getItemCount() + 1);
    }

    public static class ViewHolderItem extends RecyclerView.ViewHolder {
        public MyImageView imageView;
        public TextView priceText;
        public ProgressBar progressBar;
        public View fireIcon;
        public TextView discountText;

        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            priceText = itemView.findViewById(R.id.priceText);
            progressBar = itemView.findViewById(R.id.progressBar);
            fireIcon = itemView.findViewById(R.id.fireIcon);
            discountText = itemView.findViewById(R.id.discountText);
        }
    }

    public static class ViewHolderFooter extends RecyclerView.ViewHolder {

        public ViewHolderFooter(@NonNull View itemView) {
            super(itemView);
        }
    }

}
