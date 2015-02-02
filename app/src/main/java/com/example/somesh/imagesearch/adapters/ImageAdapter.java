package com.example.somesh.imagesearch.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.somesh.imagesearch.R;
import com.example.somesh.imagesearch.models.*;
import com.squareup.picasso.Picasso;

/**
 * Created by somesh on 1/28/15.
 */
public class ImageAdapter extends ArrayAdapter <Image>{




    public ImageAdapter(Context context, List<Image> images) {
        super(context, R.layout.image_result, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Image imageInfo = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_result, parent, false);
        }

        ImageView ivImage = (ImageView)convertView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);


        Typeface myTypeface = Typeface.createFromAsset(convertView.getContext().getAssets(), "Chantelli_Antiqua.ttf");
        tvTitle.setTypeface(myTypeface);
        ivImage.setImageResource(0);

        //populate title

        tvTitle.setText(Html.fromHtml(imageInfo.getTitle()));
        Picasso.with(getContext()).load(imageInfo.getThumbUrl()).into(ivImage);

        return convertView;
    }
}
