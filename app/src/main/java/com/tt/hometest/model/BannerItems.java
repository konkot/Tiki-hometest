package com.tt.hometest.model;

import com.google.gson.annotations.SerializedName;
import com.tt.hometest.model.Model;

import java.util.List;

public class BannerItems extends Model {
    public List<Item> data;

    @Override
    public boolean isNoData() {
        return (data == null || data.size() == 0);
    }

    public float getLowestBannerRatio() {
        if (isNoData())
            return 0;
        float minRatio = data.get(0).ratio;
        for (Item item : data) {
            if (item.ratio < minRatio)
                minRatio = item.ratio;
        }
        return minRatio;

    }

    public class Item {
        int id;
        String title;
        String content;
        String url;
        @SerializedName("image_url")
        String imageUrl;
        @SerializedName("thumbnail_url")
        String thumbnailUrl;
        @SerializedName("mobile_url")
        String mobileUrl;
        float ratio;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }

        public String getMobileUrl() {
            return mobileUrl;
        }

        public void setMobileUrl(String mobileUrl) {
            this.mobileUrl = mobileUrl;
        }

        public float getRatio() {
            return ratio;
        }

        public void setRatio(float ratio) {
            this.ratio = ratio;
        }

        /*"id":46696,
                "title":"https:\/\/tiki.vn\/chuong-trinh\/tiki-ngon-di-cho-online",
                "content":"",
                "url":"https:\/\/tiki.vn\/chuong-trinh\/tiki-ngon-di-cho-online",
                "image_url":"https:\/\/salt.tikicdn.com\/ts\/banner\/7e\/9d\/de\/03593d8e75c18b5b0b4ae60f11ae3703.jpg",
                "thumbnail_url":"",
                "mobile_url":"https:\/\/salt.tikicdn.com\/cache\/w750\/ts\/banner\/7e\/9d\/de\/6f84cdf0569cbce18c7d9e680ba79d41.jpg",
                "ratio":3.1000000000000001*/
    }

}
