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

import com.appforall.ftgrocery.databinding.FragmentSalesBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SalesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SalesFragment extends Fragment implements View.OnClickListener {

    FragmentSalesBinding salesBinding;

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
        salesBinding = FragmentSalesBinding.inflate(inflater, container, false);
        View view = salesBinding.getRoot();

        //Date picker
        salesBinding.edtSalesDate.setInputType(InputType.TYPE_NULL);

        init();
        return view;
    }

    /**
     * Initializes dbHelper
     * set up listeners
     */
    private void init() {
        dbHelper = new DBHelper(getActivity());
        salesBinding.btnSave.setOnClickListener(this);
        salesBinding.btnCancel.setOnClickListener(this);
        salesBinding.edtSalesDate.setOnClickListener(this);

    }

    /**
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == salesBinding.edtSalesDate.getId()) {
            //Date picker
            Calendar cal = Calendar.getInstance();
            int dayOfSales = cal.get(Calendar.DAY_OF_MONTH);
            int monthOfSales = cal.get(Calendar.MONTH);
            int yearOfSales = cal.get(Calendar.YEAR);
            datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    salesBinding.edtSalesDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    salesBinding.edtSalesDate.setError(null); //remove existing error
                }
            }, yearOfSales, monthOfSales, dayOfSales);
            // Set the maximum date to today's date
            datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePicker.show();

        }
        //Save Stock
        else if (v.getId() == salesBinding.btnSave.getId()) {
            if(validateForm()){
                Sales sales;
                try {
                  sales  = createSalesObj();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                isInserted = dbHelper.addSales(sales);
            if (isInserted) {
                Toast.makeText(getActivity(), "Stock added successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Failed to add stock!", Toast.LENGTH_LONG).show();
            }
        }
        }

        //Cancel and clear form
        else if (v.getId() == salesBinding.btnCancel.getId()) {
            salesBinding.edtItemCode.setText("");
            salesBinding.edtCName.setText("");
            salesBinding.edtCEmail.setText("");
            salesBinding.edtQty.setText("");
            salesBinding.edtSalesDate.setText("");
            goBackToHome();
        }
    }

    /**
     * Navigate back to home
     */
    private void goBackToHome() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(SalesFragment.this);
        transaction.commit();
    }



    private boolean validateForm() {
        boolean status = true;
        if (salesBinding.edtQty.getText().toString().trim().length() == 0 || Integer.parseInt(salesBinding.edtQty.getText().toString().trim()) < 1) {
            salesBinding.edtQty.setError("Invalid quantity");
            status = false;
        }
        //Item Code
        String itemCode = salesBinding.edtItemCode.getText().toString().trim();

        if (itemCode.length()==0) {
            salesBinding.edtItemCode.setError("Invalid item code");
            status = false;
        }else{
            //ItemCode present
            Stock stock = getStockDetails(itemCode);
            if(stock == null){
                salesBinding.edtItemCode.setError("Item code not present");
                status = false;
            }
        //Sales Quantity
            else  {
                if(Integer.parseInt(salesBinding.edtQty.getText().toString().trim())>stock.getQtyStock())
                {
                    salesBinding.edtQty.setError("Item under stock");
                    status = false;
                }
            }
        }

        //Date field
        if (salesBinding.edtSalesDate.getText() == null || salesBinding.edtSalesDate.getText().length() == 0) {
            salesBinding.edtSalesDate.setError("This field is required");
            status = false;
        }
        return status;
    }

    private Stock getStockDetails(String itemCode) {
        return dbHelper.findStockByItemCode(itemCode);
    }

    /**
     * Create entity stock object from data in fragment
     *
     * @return
     */
    private Sales createSalesObj() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date salesDate = dateFormat.parse(salesBinding.edtSalesDate.getText().toString());
        Sales sales = new Sales(Integer.parseInt(salesBinding.edtItemCode.getText().toString().trim()),
                salesBinding.edtCName.getText().toString().trim(),
                salesBinding.edtCEmail.getText().toString().trim(),
                Integer.parseInt(salesBinding.edtQty.getText().toString().trim()),
               salesDate
                );
        return sales;
    }

    public SalesFragment() {
        // Required empty public constructor
    }
}