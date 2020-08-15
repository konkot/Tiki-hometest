package com.tt.hometest.model;

import com.google.gson.annotations.SerializedName;
import com.tt.hometest.model.Model;

import java.util.List;

public class FlashDealItems extends Model {
    List<Item> data;
    public List<TabItems> tabs;
    public String version;

    public String getThumbnailUrl(int id) {
        return data.get(id).product.thumbnailUrl;
    }

    public String getUrl(int id) {
        return data.get(id).product.urlPath;
    }

    public int getSpecialPrice(int id) {
        return data.get(id).specialPrice;
    }

    public int getQty(int id) {
        return data.get(id).progress.qty;
    }

    public int getQtyOrdered(int id) {
        return data.get(id).progress.qtyOrdered;
    }

    public int getDiscountPercent(int id) {
        return data.get(id).discountPercent;
    }

    public int getItemCount() {
        return data.size();
    }

    @Override
    public boolean isNoData() {
        return data == null || data.size() == 0;
    }

    public class Item {

        int status;
        String url;
        String tags;
        @SerializedName("discount_percent")
        int discountPercent;
        @SerializedName("special_price")
        int specialPrice;
        @SerializedName("special_from_date")
        long specialFromDate;
        @SerializedName("from_date")
        String fromDate;
        @SerializedName("special_to_date")
        long specialToDate;
        Progress progress;
        Product product;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public int getDiscountPercent() {
            return discountPercent;
        }

        public void setDiscountPercent(int discountPercent) {
            this.discountPercent = discountPercent;
        }

        public int getSpecialPrice() {
            return specialPrice;
        }

        public void setSpecialPrice(int specialPrice) {
            this.specialPrice = specialPrice;
        }

        public long getSpecialFromDate() {
            return specialFromDate;
        }

        public void setSpecialFromDate(long specialFromDate) {
            this.specialFromDate = specialFromDate;
        }

        public String getFromDate() {
            return fromDate;
        }

        public void setFromDate(String fromDate) {
            this.fromDate = fromDate;
        }

        public long getSpecialToDate() {
            return specialToDate;
        }

        public void setSpecialToDate(long specialToDate) {
            this.specialToDate = specialToDate;
        }

        public Progress getProgress() {
            return progress;
        }

        public void setProgress(Progress progress) {
            this.progress = progress;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        /*"status":2,
                "url":"deal-tracking-testing",
                "tags":"testhotdeal202",
                "discount_percent":51,
                "special_price":1699000,
                "special_from_date":1597035600,
                "from_date":"2020-08-10 12:00:00",
                "special_to_date":1597068000,
                "progress":{
                            "qty":30,
                            "qty_ordered":3,
                            "qty_remain":27,
                            "percent":90,
                            "status":"progress-bar-success"
                            },
                "product":{
                    "id":40142817,
                    "sku":null,
                    "name":"B\u1ed9 dao inox Elmich EL3955 g\u1ed3m 7 m\u00f3n ( 4 dao, 1 k\u00e9o, 1 thanh m\u00e0i dao, 1 gi\u00e1 \u0111\u1ec3 dao)",
                    "url_path":"bo-dao-inox-elmich-el3955-gom-7-mon-4-dao-1-keo-1-thanh-mai-dao-1-gia-de-dao-p30026572.html?spid=40142817",
                    "price":1699000,
                    "list_price":3500000,
                    "badges":[],
                    "discount":1801000,
                    "rating_average":0,
                    "review_count":0,
                    "order_count":0,
                    "thumbnail_url":"https:\/\/salt.tikicdn.com\/cache\/280x280\/ts\/product\/83\/f1\/9c\/babfc49a8ccc0f40a5cd34f719f4ac03.jpg",
                    "is_visible":true,
                    "is_fresh":false,
                    "is_flower":false,
                    "is_gift_card":false,
                    "inventory":{
                        "product_virtual_type":null,
                        "fulfillment_type":"tiki_delivery"
                    },
                    "url_attendant_input_form":"",
                    "master_id":30026572,
                    "seller_product_id":40142817,
                    "price_prefix":"1.xxx.xxx"
                }*/
    }

    public class Progress {
        int qty;
        @SerializedName("qty_ordered")
        int qtyOrdered;
        @SerializedName("qty_remain")
        int qtyRemain;
        float percent;
        String status;

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public int getQtyOrdered() {
            return qtyOrdered;
        }

        public void setQtyOrdered(int qtyOrdered) {
            this.qtyOrdered = qtyOrdered;
        }

        public int getQtyRemain() {
            return qtyRemain;
        }

        public void setQtyRemain(int qtyRemain) {
            this.qtyRemain = qtyRemain;
        }

        public float getPercent() {
            return percent;
        }

        public void setPercent(float percent) {
            this.percent = percent;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        /*"qty":30,
                "qty_ordered":3,
                "qty_remain":27,
                "percent":90,
                "status":"progress-bar-success"*/
    }

    public class Inventory {
        @SerializedName("product_virtual_type")
        Object productVirtualType;
        @SerializedName("fulfillment_type")
        String fulfillmentType;

        public Object getProductVirtualType() {
            return productVirtualType;
        }

        public void setProductVirtualType(Object productVirtualType) {
            this.productVirtualType = productVirtualType;
        }

        public String getFulfillmentType() {
            return fulfillmentType;
        }

        public void setFulfillmentType(String fulfillmentType) {
            this.fulfillmentType = fulfillmentType;
        }

        /*"inventory":{
                    "product_virtual_type":null,
                    "fulfillment_type":"tiki_delivery"
        },*/
    }

    public class Product {
        int id;
        Object sku;
        String name;
        @SerializedName("url_path")
        String urlPath;
        int price;
        @SerializedName("list_price")
        int listPrice;
        List<Object> badges;
        int discount;
        @SerializedName("rating_average")
        int ratingAverage;
        @SerializedName("review_count")
        int reviewCount;
        @SerializedName("order_count")
        int orderCount;
        @SerializedName("thumbnail_url")
        String thumbnailUrl;
        @SerializedName("is_visible")
        boolean isVisible;
        @SerializedName("is_fresh")
        boolean isFresh;
        @SerializedName("is_flower")
        boolean isFlower;
        @SerializedName("is_gift_card")
        boolean isGiftCard;
        Inventory inventory;
        @SerializedName("url_attendant_input_form")
        String urlAttendantInputForm;
        @SerializedName("master_id")
        int masterId;
        @SerializedName("seller_product_id")
        int sellerProductId;
        @SerializedName("price_prefix")
        String pricePrefix;

       /* "product":{
                    "id":303451,
                    "sku":null,
                    "name":"L\u0103n N\u00e1ch D\u00e0nh Cho N\u1eef Aquaselin Insensitive Women Antiperspirant For Increased Perspiration 50ml - 3934",
                    "url_path":"lan-nach-danh-cho-nu-aquaselin-insensitive-women-antiperspirant-for-increased-perspiration-50ml-3934-p568256.html?spid=303451",
                    "price":277000,
                    "list_price":290000,
                    "badges":[],
                    "discount":13000,
                    "rating_average":0,
                    "review_count":0,
                    "order_count":0,
                    "thumbnail_url":"https:\/\/salt.tikicdn.com\/cache\/280x280\/ts\/product\/e7\/f8\/6c\/ded6dead50a399598bdd83cd6b59a13a.jpg",
                    "is_visible":true,
                    "is_fresh":false,
                    "is_flower":false,
                    "is_gift_card":false,
                    "inventory":{
                        "product_virtual_type":null,
                        "fulfillment_type":"tiki_delivery"
                    },
                    "url_attendant_input_form":"",
                    "master_id":568256,
                    "seller_product_id":303451,
                    "price_prefix":"2xx.xxx"
        }*/

    }

    public class TabItems {

        @SerializedName("query_value")
        int queryValue;
        @SerializedName("from_date")
        String fromDate;
        @SerializedName("to_date")
        String toDate;
        String display;
        boolean active;

        public int getQueryValue() {
            return queryValue;
        }

        public void setQueryValue(int queryValue) {
            this.queryValue = queryValue;
        }

        public String getFromDate() {
            return fromDate;
        }

        public void setFromDate(String fromDate) {
            this.fromDate = fromDate;
        }

        public String getToDate() {
            return toDate;
        }

        public void setToDate(String toDate) {
            this.toDate = toDate;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
                /*"query_value":2,
                "from_date":"2020-08-10 12:00:00",
                "to_date":"2020-08-10 21:00:00",
                "display":"12:00",
                "active":true*/


    }
}
