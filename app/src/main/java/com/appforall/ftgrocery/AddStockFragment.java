package com.appforall.ftgrocery;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appforall.ftgrocery.databinding.FragmentAddStockBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddStockFragment extends Fragment implements View.OnClickListener {

    FragmentAddStockBinding addStockBinding;
    DBHelper dbHelper;
    Boolean isInserted;
    Intent homeIntent;

    public AddStockFragment() {
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
        addStockBinding = FragmentAddStockBinding.inflate(inflater, container, false);
        View view = addStockBinding.getRoot();
        init();
        return view;
    }

    /**
     * Initializes dbHelper
     * set up listeners
     */
    private void init() {
        dbHelper = new DBHelper(getActivity());
        addStockBinding.btnSave.setOnClickListener(this);
        addStockBinding.btnCancel.setOnClickListener(this);
    }

    /**
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        //Save Stock
        if (v.getId() == addStockBinding.btnSave.getId()) {
            Stock stock = createStockObj();
            isInserted = dbHelper.addStock(stock);
            if (isInserted) {
                Toast.makeText(getActivity(), "Stock added successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Failed to add stock!", Toast.LENGTH_LONG).show();
            }
        }

        //Cancel and clear form
        else if (v.getId() == addStockBinding.btnCancel.getId()) {
            addStockBinding.edtItemName.setText("");
            addStockBinding.edtPrice.setText("");
            addStockBinding.edtQtyStock.setText("");
            addStockBinding.rgTaxable.clearCheck();
            goBackToHome();
        }
    }
    /**
     * Navigate back to home
     */
    private void goBackToHome() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(AddStockFragment.this);
        transaction.commit();
    }

    /**
     * Create entity stock object from data in fragment
     *
     * @return
     */
    private Stock createStockObj() {
        Boolean taxable = addStockBinding.rgTaxable.getCheckedRadioButtonId() == addStockBinding.rvalTaxable.getId();
        Stock stock = new Stock(addStockBinding.edtItemName.getText().toString().trim(),
                Integer.parseInt(addStockBinding.edtQtyStock.getText().toString().trim()),
                Float.parseFloat(addStockBinding.edtPrice.getText().toString().trim()),
                taxable);
        return stock;
    }
}
