package com.appforall.ftgrocery;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appforall.ftgrocery.databinding.FragmentListItemsBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListItemsFragment extends Fragment implements View.OnClickListener {
    FragmentListItemsBinding listBinding;

    List<Stock> mList = new ArrayList<Stock>();
    DBHelper dbHelper;
    ListAdapter mAdapter;

    public ListItemsFragment() {
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
        listBinding = FragmentListItemsBinding.inflate(inflater, container, false);
        View view = listBinding.getRoot();
        init();
        return view;
    }

    /**
     * Initializes variables, binds to employeeList to display
     */
    private void init() {
        listBinding.btnCancel.setOnClickListener(this);
        dbHelper = new DBHelper(getActivity());
        Cursor cursor = dbHelper.getAllStocks();
        if (cursor.getCount() < 1) {
            Toast.makeText(getActivity(), "No stocks to list", Toast.LENGTH_LONG).show();
        } else {
            cursor.moveToFirst();
            do {
                Stock stock = new Stock(cursor.getString(1), cursor.getInt(2), cursor.getFloat(3), cursor.getInt(4) == 1);
                stock.setItemCode(cursor.getInt(0));
                mList.add(stock);
            } while (cursor.moveToNext());
            cursor.close();
            dbHelper.close();
            bindAdapter();
        }
    }

    /**
     * Binds stocks list
     */
    private void bindAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        listBinding.rcView.setLayoutManager(layoutManager);
        mAdapter = new ListAdapter(mList);
        listBinding.rcView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == listBinding.btnCancel.getId()) {
            goBackToHome();
        }
    }

    /**
     * Navigate back to home
     */
    private void goBackToHome() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(ListItemsFragment.this);
        transaction.commit();
    }
}