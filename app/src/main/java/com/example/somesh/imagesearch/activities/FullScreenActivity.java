package com.example.somesh.imagesearch.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.somesh.imagesearch.R;
import com.example.somesh.imagesearch.models.Image;
import com.squareup.picasso.Picasso;

public class FullScreenActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Image image = (Image) getIntent().getParcelableExtra("imageInfo");
        ImageView ivFull = (ImageView)findViewById(R.id.ivFullImage);
        Picasso.with(this).load(image.getFullUrl()).into(ivFull);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_full_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
