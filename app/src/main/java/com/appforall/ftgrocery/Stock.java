package com.appforall.ftgrocery;

import org.intellij.lang.annotations.Identifier;

import javax.annotation.processing.Generated;

/**
 * Table - Stock
 * itemCode PK Integer, itemName Text, qtyStock Integer, price Float, taxable Boolean
 */
public class Stock {
    private Integer itemCode;
    private String itemName;
    private Integer qtyStock;
    private Float price;
    private Boolean taxable;

    public Stock() {
    }

    public Stock(String itemName, Integer qtyStock, Float price, Boolean taxable) {
        this.itemName = itemName;
        this.qtyStock = qtyStock;
        this.price = price;
        this.taxable = taxable;
    }

    public Integer getItemCode() {
        return itemCode;
    }

    public void setItemCode(Integer itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQtyStock() {
        return qtyStock;
    }

    public void setQtyStock(Integer qtyStock) {
        this.qtyStock = qtyStock;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Boolean getTaxable() {
        return taxable;
    }

    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }
}
