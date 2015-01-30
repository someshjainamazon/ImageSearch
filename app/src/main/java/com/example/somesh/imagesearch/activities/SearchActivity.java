package com.example.somesh.imagesearch.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.somesh.imagesearch.R;
import com.example.somesh.imagesearch.adapters.ImageAdapter;
import com.example.somesh.imagesearch.models.*;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {

    private EditText etQuery;
    private GridView gvImages;
    private ArrayList<Image> imageResults;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.search_icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("     ImageSearch");
        setupViews();
        imageResults = new ArrayList<Image>();
        imageAdapter = new ImageAdapter(this, imageResults);
        gvImages.setAdapter(imageAdapter);


    }

    private void setupViews() {

        etQuery = (EditText) findViewById(R.id.etQuery);
        gvImages = (GridView) findViewById(R.id.gvItems);

        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchActivity.this, FullScreenActivity.class);
                Image imageInfo = imageResults.get(position);
                i.putExtra("imageInfo",imageInfo);
                startActivity(i);

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.filter_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onImageSearch(View view) {

        String query = etQuery.getText().toString();
        String queryUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+query+"&rsz=8";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(queryUrl, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    JSONArray imagesJson = response.getJSONObject("responseData").getJSONArray("results");
                    imageResults.clear();
                    imageAdapter.addAll(Image.getImageArrayList(imagesJson));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i("INFO", imageResults.toString());
            }
        });


    }


}
