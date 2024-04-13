package com.appforall.ftgrocery;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.appforall.ftgrocery.databinding.FragmentPurchaseBinding;
import com.appforall.ftgrocery.databinding.FragmentSalesBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseFragment extends Fragment implements View.OnClickListener {
    public PurchaseFragment() {
        // Required empty public constructor
    }

    FragmentPurchaseBinding purchaseBinding;
    DBHelper dbHelper;
    Boolean isInserted;
    DatePickerDialog datePicker;


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
        purchaseBinding = FragmentPurchaseBinding.inflate(inflater, container, false);
        View view = purchaseBinding.getRoot();

        //Date picker
        purchaseBinding.edtPurchaseDate.setInputType(InputType.TYPE_NULL);

        init();
        return view;
    }

    /**
     * Initializes dbHelper
     * set up listeners
     */
    private void init() {
        dbHelper = new DBHelper(getActivity());
        purchaseBinding.btnSubmit.setOnClickListener(this);
        purchaseBinding.btnCancel.setOnClickListener(this);
        purchaseBinding.edtPurchaseDate.setOnClickListener(this);

    }

    /**
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == purchaseBinding.edtPurchaseDate.getId()) {
            //Date picker
            Calendar cal = Calendar.getInstance();
            int dayOfSales = cal.get(Calendar.DAY_OF_MONTH);
            int monthOfSales = cal.get(Calendar.MONTH);
            int yearOfSales = cal.get(Calendar.YEAR);
            datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    purchaseBinding.edtPurchaseDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    purchaseBinding.edtPurchaseDate.setError(null); //remove existing error
                }
            }, yearOfSales, monthOfSales, dayOfSales);
            // Set the maximum date to today's date
            datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePicker.show();
        }
        //Save Purchase
        else if (v.getId() == purchaseBinding.btnSubmit.getId()) {
            if (validateForm()) {
                Purchase purchase;
                try {
                    purchase = createPurchaseObj();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                isInserted = dbHelper.addPurchase(purchase);
                if (isInserted) {
                    Toast.makeText(getActivity(), "Purchase recorded successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Failed to update purchase!", Toast.LENGTH_LONG).show();
                }
            }
        }

        //Cancel and clear form
        else if (v.getId() == purchaseBinding.btnCancel.getId()) {
            purchaseBinding.edtItemCode.setText("");
            purchaseBinding.edtQty.setText("");
            purchaseBinding.edtPurchaseDate.setText("");
            goBackToHome();
        }
    }

    /**
     * Navigate back to home
     */
    private void goBackToHome() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(PurchaseFragment.this);
        transaction.commit();
    }


    /**
     * Validates form
     *
     * @return
     */
    private boolean validateForm() {
        boolean status = true;
        if (purchaseBinding.edtQty.getText().toString().trim().length() == 0 || Integer.parseInt(purchaseBinding.edtQty.getText().toString().trim()) < 1) {
            purchaseBinding.edtQty.setError("Invalid quantity");
            status = false;
        }
        //Item Code
        String itemCode = purchaseBinding.edtItemCode.getText().toString().trim();

        if (itemCode.length() == 0) {
            purchaseBinding.edtItemCode.setError("Invalid item code");
            status = false;
        } else {
            //ItemCode not empty
            Stock stock = getStockDetails(itemCode);
            if (stock == null) {
                purchaseBinding.edtItemCode.setError("Item code not present");
                status = false;
            }
        }

        //Date field
        if (purchaseBinding.edtPurchaseDate.getText() == null || purchaseBinding.edtPurchaseDate.getText().length() == 0) {
            purchaseBinding.edtPurchaseDate.setError("This field is required");
            status = false;
        }
        return status;
    }

    /**
     * Get Stock Details from DB
     *
     * @param itemCode
     * @return Stock
     */
    private Stock getStockDetails(String itemCode) {
        return dbHelper.findStockByItemCode(itemCode);
    }

    /**
     * Create entity stock object from data in fragment
     *
     * @return
     */
    private Purchase createPurchaseObj() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date purchaseDate = dateFormat.parse(purchaseBinding.edtPurchaseDate.getText().toString());
        return new Purchase(Integer.parseInt(purchaseBinding.edtItemCode.getText().toString().trim()),
                Integer.parseInt(purchaseBinding.edtQty.getText().toString().trim()),
                purchaseDate);
    }

}