package com.tt.hometest.model;

import com.google.gson.annotations.SerializedName;
import com.tt.hometest.model.Model;

import java.util.List;

public class QuickLinkItems extends Model {

    public List<List<Item>> data;

    public List<Item> getRow(int rowId) {
        return data.get(rowId);
    }

    public int getRowCount() {
        return data.size();
    }

    @Override
    public boolean isNoData() {
        return data == null || data.size() == 0 || data.get(0) == null || data.get(0).size() == 0;
    }


    public class Item {
        String title;
        String content;
        String url;
        String authentication;
        @SerializedName("web_url")
        String webUrl;
        @SerializedName("image_url")
        String imageUrl;

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

        public String getAuthentication() {
            return authentication;
        }

        public void setAuthentication(String authentication) {
            this.authentication = authentication;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }


            /*"title":"Mã giảm giá",
            "content":"",
            "url":"https://tiki.vn/chuong-trinh/ma-giam-gia",
            "authentication":false,
            "web_url":"",
            "image_url":"https://salt.tikicdn.com/ts/upload/73/50/e1/83afc85db37c472de60ebef6eceb41a7.png"*/
}
