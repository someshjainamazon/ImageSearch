package com.example.somesh.imagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by somesh on 1/29/15.
 */
public class QueryFilter implements Parcelable {

    private String color;
    private String imageSize;
    private String imageType;
    private String siteFilter;



    public String getSiteFilter() {
        return siteFilter;
    }

    public void setSiteFilter(String siteFilter) {
        this.siteFilter = siteFilter;
    }



    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(color);
        dest.writeString(imageSize);
        dest.writeString(imageType);
        dest.writeString(siteFilter);

    }

    public static final Parcelable.Creator<QueryFilter> CREATOR
            = new Parcelable.Creator<QueryFilter>() {
        @Override
        public QueryFilter createFromParcel(Parcel in) {
            return new QueryFilter(in);
        }

        @Override
        public QueryFilter[] newArray(int size) {
            return new QueryFilter[size];
        }
    };

    private  QueryFilter(Parcel p){
        color=p.readString();
        imageSize=p.readString();
        imageType=p.readString();
        siteFilter=p.readString();
    }

    public QueryFilter(){

    }

}
