package com.appforall.ftgrocery;

import java.util.Date;

/**
 * Table - Sales
 * orderNumber PK Integer, itemCode Integer, customerName Text, customerEmail Text, qtySold Integer,and dateOfSales Date
 */
public class Sales {
    private Integer orderNumber;
    private Integer itemCode;
    private String customerName;
    private String customerEmail;
    private Integer qtySold;
    private Date dateOfSales;

    public Sales(Integer itemCode, String customerName, String customerEmail, Integer qtySold, Date dateOfSales) {
        this.itemCode = itemCode;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.qtySold = qtySold;
        this.dateOfSales = dateOfSales;
    }

    public Sales() {
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getItemCode() {
        return itemCode;
    }

    public void setItemCode(Integer itemCode) {
        this.itemCode = itemCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Integer getQtySold() {
        return qtySold;
    }

    public void setQtySold(Integer qtySold) {
        this.qtySold = qtySold;
    }

    public Date getDateOfSales() {
        return dateOfSales;
    }

    public void setDateOfSales(Date dateOfSales) {
        this.dateOfSales = dateOfSales;
    }
}
