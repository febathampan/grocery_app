package com.appforall.ftgrocery;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appforall.ftgrocery.databinding.FragmentPurchaseBinding;
import com.appforall.ftgrocery.databinding.FragmentSearchBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.ParseException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {
    FragmentSearchBinding searchBinding;
    DBHelper dbHelper;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        searchBinding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = searchBinding.getRoot();
        init();
        return view;
    }

    /**
     * Initializes dbHelper
     * set up listeners
     */
    private void init() {
        dbHelper = new DBHelper(getActivity());
        searchBinding.btnSearch.setOnClickListener(this);
        searchBinding.btnCancel.setOnClickListener(this);

    }

    /**
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == searchBinding.btnSearch.getId()) {
            Stock stock = dbHelper.findStockByItemCode(searchBinding.edtItemCode.getText().toString().trim());
            if (validateForm()) {
                if (stock == null) {
                    //Using Material Alert Dialog
                    new MaterialAlertDialogBuilder(getActivity())
                            .setMessage("Stock with given itemId not found!")
                            .setTitle("Stock not found")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Toast.makeText(getActivity(), "OK Clicked!",Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Toast.makeText(getActivity(), "Cancel Clicked!", Toast.LENGTH_LONG).show();
                                }
                            }).show();
                } else {
                    //Using Material Alert Dialog
                    new MaterialAlertDialogBuilder(getActivity())
                            .setMessage("Item name:" + stock.getItemName() + "\nQty in stock: " + stock.getQtyStock() + "\nPrice: " + stock.getPrice())
                            .setTitle("Stock Details")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Toast.makeText(getActivity(), "OK Clicked!",Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Toast.makeText(getActivity(), "Cancel Clicked!", Toast.LENGTH_LONG).show();
                                }
                            }).show();

                }
            }
        }
        //Cancel and clear form
        else if (v.getId() == searchBinding.btnCancel.getId()) {
            searchBinding.edtItemCode.setText("");
            goBackToHome();
        }

    }

    /**
     * Validates form
     *
     * @return
     */
    private boolean validateForm() {
        boolean status = true;
        //Item Code
        String itemCode = searchBinding.edtItemCode.getText().toString().trim();

        if (itemCode.length() == 0) {
            searchBinding.edtItemCode.setError("Invalid item code");
            status = false;
        }
        return status;
    }

    /**
     * Navigate back to home
     */
    private void goBackToHome() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(SearchFragment.this);
        transaction.commit();
    }
}