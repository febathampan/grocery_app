package com.appforall.ftgrocery;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appforall.ftgrocery.databinding.RecordLayoutBinding;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Stock> stocks;

    RecordLayoutBinding recordLayoutBinding;

    /**
     * Assign stocks list
     *
     * @param stocks
     */
    public ListAdapter(List<Stock> stocks) {
        super();
        this.stocks = stocks;
    }
    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        recordLayoutBinding = RecordLayoutBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(recordLayoutBinding);
    }
    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindView(stocks.get(position));
    }
    @Override
    public int getItemCount() {
        return stocks.size();
    }
}

//ViewHolder class
class ViewHolder extends RecyclerView.ViewHolder {
    RecordLayoutBinding recyclerRowBinding;

    /**
     * Represents rows
     *
     * @param recyclerRowBinding
     */
    public ViewHolder(RecordLayoutBinding recyclerRowBinding) {
        super(recyclerRowBinding.getRoot());
        this.recyclerRowBinding = recyclerRowBinding;
    }

    /**
     * Bind stocks view
     *
     * @param stock
     */
    public void bindView(Stock stock) {
        recyclerRowBinding.txtItemCode.setText(String.valueOf(stock.getItemCode()));
        recyclerRowBinding.txtItemName.setText(stock.getItemName());
        recyclerRowBinding.txtPrice.setText(String.valueOf(stock.getPrice()));
        recyclerRowBinding.txtTaxable.setText(stock.getTaxable()?"Yes":"No");
        recyclerRowBinding.txtQty.setText(String.valueOf(stock.getQtyStock()));
    }
}
