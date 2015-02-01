package com.example.somesh.imagesearch.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.somesh.imagesearch.R;
import com.example.somesh.imagesearch.models.Image;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.security.auth.callback.Callback;

public class FullScreenActivity extends ActionBarActivity {



    private ShareActionProvider miShareAction;


    public ShareActionProvider getMiShareAction() {
        return miShareAction;
    }

    public void setMiShareAction(ShareActionProvider miShareAction) {
        this.miShareAction = miShareAction;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        if (getSupportActionBar() != null) {
            //getSupportActionBar().hide(); - uncomment this later - testing for share action right now
        }

        Image image = (Image) getIntent().getParcelableExtra("imageInfo");
        ImageView ivFull = (ImageView)findViewById(R.id.ivFullImage);
        //Picasso.with(this).load(image.getFullUrl()).into(ivFull);  -- testing uncomment later

        Picasso.with(this).load(image.getFullUrl()).into(ivFull, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

                setupShareIntent();

            }

            @Override
            public void onError() {

            }
        });




    }

    private void setupShareIntent() {

        ImageView ivImage = (ImageView) findViewById(R.id.ivFullImage);
        Uri bmpUri = getLocalBitmapUri(ivImage); // see previous remote images section
        // Create share intent as described above
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        shareIntent.setType("image/*");
        // Attach share event to the menu item provider
        miShareAction.setShareIntent(shareIntent);
    }


    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_full_screen, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_share);

        miShareAction = new ShareActionProvider(this);
        MenuItemCompat.setActionProvider(menuItem, miShareAction);
        return super.onCreateOptionsMenu(menu);
        //return true;
        //miShareAction = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        // Return true to display menu


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
