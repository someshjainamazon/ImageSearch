package com.example.somesh.imagesearch.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.somesh.imagesearch.R;
import com.example.somesh.imagesearch.models.QueryFilter;

public class SettingsActivity extends ActionBarActivity {


    QueryFilter oldQuery;
    Spinner imageSizeSpinner;
    Spinner colorFilterSpinner;
    Spinner imageTypeSpinner;
    EditText etSiteFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        oldQuery = (QueryFilter)getIntent().getParcelableExtra("currentQuery");
        imageSizeSpinner = (Spinner) findViewById(R.id.spnImageSize);
        colorFilterSpinner = (Spinner) findViewById(R.id.spnColorFilter);
        imageTypeSpinner = (Spinner) findViewById(R.id.spnImageType);
        etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);

        if(oldQuery.getImageSize()!=null) setSpinnerToValue(imageSizeSpinner, oldQuery.getImageSize());


        if(oldQuery.getColor()!=null) setSpinnerToValue(colorFilterSpinner, oldQuery.getColor());


        if(oldQuery.getImageType()!=null) setSpinnerToValue(imageTypeSpinner, oldQuery.getImageType());


        if(oldQuery.getSiteFilter()!=null) etSiteFilter.setText(oldQuery.getSiteFilter().toString());

    }

    private void setSpinnerToValue(Spinner spinner, String value) {


        int index = 0;
        SpinnerAdapter adapter = spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(value)) {
                index = i;
                break; // terminate loop
            }
        }
        spinner.setSelection(index);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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

    public void setNewFilters(View view) {

        QueryFilter queryFilter = new QueryFilter();

        if(imageSizeSpinner.getSelectedItem()!=null && imageSizeSpinner.getSelectedItem().toString()!="---") queryFilter.setImageSize(imageSizeSpinner.getSelectedItem().toString());
        if(colorFilterSpinner.getSelectedItem()!=null && colorFilterSpinner.getSelectedItem().toString()!="---") queryFilter.setColor(colorFilterSpinner.getSelectedItem().toString());
        if(imageTypeSpinner.getSelectedItem()!=null && imageTypeSpinner.getSelectedItem().toString()!="---") queryFilter.setImageType(imageTypeSpinner.getSelectedItem().toString());
        if(etSiteFilter.getText()!=null)queryFilter.setSiteFilter(etSiteFilter.getText().toString());
        Intent i = new Intent();
        i.putExtra("newSets", queryFilter);
        setResult(RESULT_OK, i);
        this.finish();
    }



}
