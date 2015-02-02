package com.example.somesh.imagesearch.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
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


public class SearchActivity extends ActionBarActivity implements EditDialog.EditDialogListener{

    //private EditText etQuery;
    //private GridView gvImages;
    private StaggeredGridView gvImages;
    private ArrayList<Image> imageResults;
    private ImageAdapter imageAdapter;
    public static final int REQUEST_RESULT=50;
    private QueryFilter queryFilter;
    String finalQuery;
    String actionBarQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /*getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.drawable.search_icon);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("  ImageSearch");*/


        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_actionbar);

        Button btnCancel = (Button) findViewById(R.id.btnCross);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etQuery = (EditText) findViewById(R.id.etQuery);
                etQuery.setText("");
            }
        });



        setupViews();
        imageResults = new ArrayList<Image>();
        imageAdapter = new ImageAdapter(this, imageResults);
        gvImages.setAdapter(imageAdapter);
        queryFilter = new QueryFilter();

    }


    private void showEditDialog() {

        FragmentManager fm = getSupportFragmentManager();
        EditDialog editNameDialog = EditDialog.newInstance(queryFilter);
        editNameDialog.show(fm, "activity_settings");
    }

    private void setupViews() {

        gvImages = (StaggeredGridView) findViewById(R.id.gvItems);
        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchActivity.this, FullScreenActivity.class);
                Image imageInfo = imageResults.get(position);
                i.putExtra("imageInfo",imageInfo);
                startActivity(i);

            }
        });


        if(isInternetAvailable()) {
            gvImages.setOnScrollListener(new EndlessScrollListener() {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    loadMoreData(page);


                }
            });
        }

    }

    private void loadMoreData(int page) {

        String infiniteQuery = finalQuery+"&start="+page;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(infiniteQuery, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    JSONArray imagesJson = response.getJSONObject("responseData").getJSONArray("results");
                    imageAdapter.addAll(Image.getImageArrayList(imagesJson));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i("INFO", imageResults.toString());
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                boolean isAvailable = isInternetAvailable();

                if(isAvailable==false){
                    System.out.println("internet is not available");
                }
            }
        });

    }

    private boolean isInternetAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null && networkInfo.isConnectedOrConnecting();
    }


    /*onCreateOptionsMenu deals with the action bar */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);



        /*MenuItem searchItem = menu.findItem(R.id.action_search);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                System.out.println("hello");
                actionBarQuery=s;
                constructQuery(actionBarQuery);
                onImageSearchFromActionBar();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });*/

        final EditText etQuery = (EditText) findViewById(R.id.etQuery);
        etQuery.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    actionBarQuery=etQuery.getText().toString();
                    constructQuery(actionBarQuery);
                    onImageSearchFromActionBar();
                    return true;
                }
                return false;
            }
        });


        /*final EditText editText = (EditText)findViewById(R.id.etQuery);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    Toast.makeText(SearchActivity.this, editText.getText(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });*/

        return true;



        //return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.filter_settings) {
            showEditDialog();

            /*Intent i = new Intent(this, SettingsActivity.class);
            i.putExtra("currentQuery", queryFilter);
            startActivityForResult(i, REQUEST_RESULT);*/
        }

        return super.onOptionsItemSelected(item);
    }


    public void onImageSearchFromActionBar() {

        String queryUrl = constructQuery(actionBarQuery);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(queryUrl, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    JSONArray imagesJson = response.getJSONObject("responseData").getJSONArray("results");
                    //imageResults.clear();
                    imageAdapter.clear();
                    imageAdapter.addAll(Image.getImageArrayList(imagesJson));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i("INFO", imageResults.toString());
            }
        });


    }



    private String constructQuery(String s) {

        finalQuery = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+s+"&rsz=8";

        if(queryFilter.getImageSize()!=null) finalQuery=finalQuery+"&imgsz="+queryFilter.getImageSize().toString();
        if(queryFilter.getColor()!=null) finalQuery=finalQuery+"&imgcolor="+queryFilter.getColor().toString();
        if(queryFilter.getImageType()!=null) finalQuery=finalQuery+"&imgtype="+queryFilter.getImageType().toString();
        if(queryFilter.getSiteFilter()!=null && !queryFilter.getSiteFilter().isEmpty()) finalQuery=finalQuery+"&as_sitesearch="+queryFilter.getSiteFilter().toString();

        return finalQuery;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(requestCode==REQUEST_RESULT){
            if(resultCode==RESULT_OK){
                queryFilter = data.getParcelableExtra("newSets");
                //onImageSearch(null);   --- change this later
                onImageSearchFromActionBar();
            }
        }

    }



    @Override
    public void onFinishEditDialog(QueryFilter queryFilterNew) {

        queryFilter = queryFilterNew;
        if(actionBarQuery!=null && !actionBarQuery.isEmpty())   onImageSearchFromActionBar();

    }
}
