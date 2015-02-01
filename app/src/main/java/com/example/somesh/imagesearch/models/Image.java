package com.example.somesh.imagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by somesh on 1/28/15.
 */
public class Image implements Parcelable{


    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String fullUrl;

    private String thumbUrl;

    private String title;

    public static Image parseImage(JSONObject imageJson){

        Image image = new Image();
        try {
            image.fullUrl=imageJson.getString("url");
            image.thumbUrl=imageJson.getString("tbUrl");
            image.title=imageJson.getString("title");
            return image;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static ArrayList<Image> getImageArrayList(JSONArray imageArray){

        ArrayList<Image> arrayListImages = new ArrayList<Image>();
        for (int i=0;i<imageArray.length();i++){
            try {
                arrayListImages.add(parseImage(imageArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return arrayListImages;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullUrl);
        dest.writeString(thumbUrl);
        dest.writeString(title);
    }

    public static final Parcelable.Creator<Image> CREATOR
            = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };


    private Image(Parcel in) {
        fullUrl=in.readString();
        thumbUrl=in.readString();
        title=in.readString();
    }

    public Image() {
        //normal actions performed by class, it's still a normal object!
    }


}
