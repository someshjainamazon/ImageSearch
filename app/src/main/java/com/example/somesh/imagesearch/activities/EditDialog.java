package com.example.somesh.imagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.somesh.imagesearch.R;
import com.example.somesh.imagesearch.models.QueryFilter;

/**
 * Created by somesh on 1/31/15.
 */
public class EditDialog extends DialogFragment implements View.OnClickListener {




    QueryFilter oldQuery;
    Spinner imageSizeSpinner;
    Spinner colorFilterSpinner;
    Spinner imageTypeSpinner;
    EditText etSiteFilter;
    Button saveButton;

    @Override
    public void onClick(View v) {
        QueryFilter queryFilter = new QueryFilter();

        if(imageSizeSpinner.getSelectedItem()!=null && imageSizeSpinner.getSelectedItem().toString()!="---") queryFilter.setImageSize(imageSizeSpinner.getSelectedItem().toString());
        if(colorFilterSpinner.getSelectedItem()!=null && colorFilterSpinner.getSelectedItem().toString()!="---") queryFilter.setColor(colorFilterSpinner.getSelectedItem().toString());
        if(imageTypeSpinner.getSelectedItem()!=null && imageTypeSpinner.getSelectedItem().toString()!="---") queryFilter.setImageType(imageTypeSpinner.getSelectedItem().toString());
        if(etSiteFilter.getText()!=null)queryFilter.setSiteFilter(etSiteFilter.getText().toString());


        EditDialogListener listener = (EditDialogListener) getActivity();
        listener.onFinishEditDialog(queryFilter);
        dismiss();
    }

    public interface EditDialogListener {
        void onFinishEditDialog(QueryFilter queryFilter);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings, container);

        oldQuery =  getArguments().getParcelable("currentQuery");
        imageSizeSpinner = (Spinner)view.findViewById(R.id.spnImageSize);
        colorFilterSpinner = (Spinner)view.findViewById(R.id.spnColorFilter);
        imageTypeSpinner = (Spinner)view.findViewById(R.id.spnImageType);
        etSiteFilter = (EditText)view.findViewById(R.id.etSiteFilter);
        saveButton = (Button) view.findViewById(R.id.btnSave);
        saveButton.setImeOptions(EditorInfo.IME_ACTION_GO);
        //saveButton.setRawInputType(InputType.);

        if(oldQuery.getImageSize()!=null) setSpinnerToValue(imageSizeSpinner, oldQuery.getImageSize());


        if(oldQuery.getColor()!=null) setSpinnerToValue(colorFilterSpinner, oldQuery.getColor());


        if(oldQuery.getImageType()!=null) setSpinnerToValue(imageTypeSpinner, oldQuery.getImageType());


        if(oldQuery.getSiteFilter()!=null) etSiteFilter.setText(oldQuery.getSiteFilter().toString());

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        saveButton.setOnClickListener(this);

        /*
        saveButton.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (EditorInfo.IME_ACTION_GO == actionId) {
                    // Return input text to activity

                    QueryFilter queryFilter = new QueryFilter();

                    if(imageSizeSpinner.getSelectedItem()!=null && imageSizeSpinner.getSelectedItem().toString()!="---") queryFilter.setImageSize(imageSizeSpinner.getSelectedItem().toString());
                    if(colorFilterSpinner.getSelectedItem()!=null && colorFilterSpinner.getSelectedItem().toString()!="---") queryFilter.setColor(colorFilterSpinner.getSelectedItem().toString());
                    if(imageTypeSpinner.getSelectedItem()!=null && imageTypeSpinner.getSelectedItem().toString()!="---") queryFilter.setImageType(imageTypeSpinner.getSelectedItem().toString());
                    if(etSiteFilter.getText()!=null)queryFilter.setSiteFilter(etSiteFilter.getText().toString());


                    EditDialogListener listener = (EditDialogListener) getActivity();
                    listener.onFinishEditDialog(queryFilter);
                    dismiss();
                    return true;
                }
                return false;
            }
        });*/
        return view;



    }

    public static EditDialog newInstance(QueryFilter queryFilter) {
        EditDialog frag = new EditDialog();
        Bundle args = new Bundle();
        //args.putString("title", title);
        args.putParcelable("currentQuery", queryFilter);
        frag.setArguments(args);
        return frag;
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


    public EditDialog() {
        // Empty constructor required for DialogFragment
    }

    /*
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
    }*/

    /*
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        System.out.println("hello");
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity

            QueryFilter queryFilter = new QueryFilter();

            if(imageSizeSpinner.getSelectedItem()!=null && imageSizeSpinner.getSelectedItem().toString()!="---") queryFilter.setImageSize(imageSizeSpinner.getSelectedItem().toString());
            if(colorFilterSpinner.getSelectedItem()!=null && colorFilterSpinner.getSelectedItem().toString()!="---") queryFilter.setColor(colorFilterSpinner.getSelectedItem().toString());
            if(imageTypeSpinner.getSelectedItem()!=null && imageTypeSpinner.getSelectedItem().toString()!="---") queryFilter.setImageType(imageTypeSpinner.getSelectedItem().toString());
            if(etSiteFilter.getText()!=null)queryFilter.setSiteFilter(etSiteFilter.getText().toString());


            EditDialogListener listener = (EditDialogListener) getActivity();
            listener.onFinishEditDialog(queryFilter);
            dismiss();
            return true;
        }
        return false;

    }*/


}
