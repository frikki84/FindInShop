package com.example.findinshop.by.parsing.model.entity.results;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.Log;

import androidx.loader.content.Loader;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class ResultInfo implements Parcelable {
    public static final String DEFAULT_PRODUCT_NAME = "Товар не найден";
    public static final String DEFAULT_IMAGE_URL = "http://zabavnik.club/wp-content/uploads/2018/07/net_izobrazheniya_1_20174640.png";


    private String productName;
    private ArrayList<String> shopPriceInfo;
    private ArrayList<String> linksList;
    private String imageUrl;


    public ResultInfo() {
        productName = DEFAULT_PRODUCT_NAME;
        imageUrl = DEFAULT_IMAGE_URL;
        shopPriceInfo = new ArrayList<>();
        linksList = new ArrayList<>();
    }

    public ResultInfo(String productName) {
        this.productName = productName;
        this.imageUrl = DEFAULT_IMAGE_URL;
        imageUrl = DEFAULT_IMAGE_URL;
        shopPriceInfo = new ArrayList<>();
        linksList = new ArrayList<>();
    }

    public ResultInfo(String productName, String imageUrl) {
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.shopPriceInfo = new ArrayList<>();
        this.linksList = new ArrayList<>();
    }

    public ResultInfo(String productName, ArrayList<String> shopPriceInfo) {
        this.productName = productName;
        this.shopPriceInfo = shopPriceInfo;
        this.linksList = new ArrayList<>();
        this.imageUrl = DEFAULT_IMAGE_URL;
    }

    public ResultInfo(String productName, ArrayList<String> shopPriceInfo, String imageUrl) {
        this.productName = productName;
        this.shopPriceInfo = shopPriceInfo;
        this.imageUrl = imageUrl;
        this.linksList = new ArrayList<>();
    }

    public ResultInfo(String productName, ArrayList<String> shopPriceInfo, ArrayList<String> linksList, String imageUrl) {
        this.productName = productName;
        this.shopPriceInfo = shopPriceInfo;
        this.linksList = linksList;
        this.imageUrl = imageUrl;
    }

    public ResultInfo(ResultInfo resultInfo) {
        this.productName = resultInfo.getProductName();
        this.imageUrl = resultInfo.getImageUrl();
        this.shopPriceInfo = resultInfo.getShopPriceInfo();
        this.linksList = resultInfo.getLinksList();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ArrayList<String> getShopPriceInfo() {
        return shopPriceInfo;
    }

    public void setShopPriceInfo(ArrayList<String> shopPriceInfo) {
        this.shopPriceInfo = shopPriceInfo;
    }

    public ArrayList<String> getLinksList() {
        return linksList;
    }

    public void setLinksList(ArrayList<String> linksList) {
        this.linksList = linksList;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static Creator<ResultInfo> getCREATOR() {
        return CREATOR;
    }

    protected ResultInfo(Parcel in) {
        productName = in.readString();
        imageUrl = in.readString();
        shopPriceInfo = in.createStringArrayList();
        linksList = in.createStringArrayList();

    }

    public static final Creator<ResultInfo> CREATOR = new Creator<ResultInfo>() {
        @Override
        public ResultInfo createFromParcel(Parcel in) {
            return new ResultInfo(in);
        }

        @Override
        public ResultInfo[] newArray(int size) {
            return new ResultInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productName);
        parcel.writeString(imageUrl);
        parcel.writeStringList(shopPriceInfo);
        parcel.writeStringList(linksList);

    }
}
