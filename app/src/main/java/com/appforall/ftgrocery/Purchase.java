package com.appforall.ftgrocery;

import java.util.Date;

public class Purchase {

    private Integer invoiceNumber;
    private Integer itemCode;
    private Integer qtyPurchased;
    private Date dateOfPurchase;

    public Purchase() {
    }

    public Purchase(Integer invoiceNumber, Integer itemCode, Integer qtyPurchased, Date dateOfPurchase) {
        this.invoiceNumber = invoiceNumber;
        this.itemCode = itemCode;
        this.qtyPurchased = qtyPurchased;
        this.dateOfPurchase = dateOfPurchase;
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Integer invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Integer getItemCode() {
        return itemCode;
    }

    public void setItemCode(Integer itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getQtyPurchased() {
        return qtyPurchased;
    }

    public void setQtyPurchased(Integer qtyPurchased) {
        this.qtyPurchased = qtyPurchased;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }
}
