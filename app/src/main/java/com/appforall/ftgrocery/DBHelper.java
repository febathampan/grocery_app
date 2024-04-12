package com.appforall.ftgrocery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    static final String DBNAME = "FTGrocery.db";
    static final int VERSION = 1;
    static final String USER_TABLE = "User";
    static final String USER_PK_ID = "id";
    static final String USER_EMAIL = "emailId";
    static final String USER_PASSWORD = "password";
    static final String USER_UNAME = "userName";
    static final String STOCK_TABLE = "Stock";
    static final String STOCK_PK_ITEM_CODE = "itemCode";
    static final String STOCK_ITEM_NAME = "itemName";
    static final String STOCK_QTY = "qtyStock";
    static final String STOCK_PRICE = "price";
    static final String STOCK_TAXABLE = "taxable";
    static final String SALES_TABLE = "Sales";
    static final String SALES_PK_ORDER_NUMBER= "orderNumber";
    static final String SALES_ITEM_CODE = "itemCode";
    static final String SALES_C_NAME = "customerName";
    static final String SALES_C_EMAIL = "customerEmail";
    static final String SALES_QTY_SOLD = "qtySold";
    static final String SALES_DATE = "dateOfSales";
    static final String PURCHASE_TABLE = "Purchase";
    static final String PURCHASE_PK_INVOICE_NUM = "invoiceNumber";
    static final String PURCHASE_ITEM_CODE = "itemCode";
    static final String PURCHASE_QTY = "qtyPurchased";
    static final String PURCHASE_DATE = "dateOfPurchase";
    static final String CREATE_TABLE_USER = "create table " + USER_TABLE + " (" + USER_PK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_EMAIL + " TEXT, "
            + USER_UNAME + " TEXT UNIQUE NOT NULL, " + USER_PASSWORD + " TEXT NOT NULL); ";
    static final String CREATE_TABLE_STOCK = "create table " + STOCK_TABLE + " (" + STOCK_PK_ITEM_CODE + " INTEGER PRIMARY KEY AUTOINCREMENT, " + STOCK_ITEM_NAME + " TEXT NOT NULL, "
            + STOCK_QTY + " INTEGER, " + STOCK_PRICE + " REAL, " + STOCK_TAXABLE + " INTEGER); ";
    static final String CREATE_TABLE_SALES = "create table " + SALES_TABLE + " (" + SALES_PK_ORDER_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SALES_ITEM_CODE + " INTEGER NOT NULL, "
            + SALES_C_NAME + " TEXT, " + SALES_C_EMAIL + " TEXT, " + SALES_QTY_SOLD + " INTEGER, " + SALES_DATE + " TEXT); ";

    static final String CREATE_TABLE_PURCHASE = "create table " + PURCHASE_TABLE + " (" + PURCHASE_PK_INVOICE_NUM + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PURCHASE_ITEM_CODE + " INTEGER NOT NULL, "
            + PURCHASE_QTY + " INTEGER, " + PURCHASE_DATE + " TEXT); ";
    static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS " + USER_TABLE;
    static final String DROP_TABLE_STOCK = "DROP TABLE IF EXISTS " + STOCK_TABLE;
    static final String DROP_TABLE_SALES = "DROP TABLE IF EXISTS " + SALES_TABLE;
    static final String DROP_TABLE_PURCHASE = "DROP TABLE IF EXISTS " + PURCHASE_TABLE;

    public DBHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_STOCK);
        db.execSQL(CREATE_TABLE_SALES);
        db.execSQL(CREATE_TABLE_PURCHASE);

    }

    /**
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USER);
        db.execSQL(DROP_TABLE_SALES);
        db.execSQL(DROP_TABLE_STOCK);
        db.execSQL(DROP_TABLE_PURCHASE);
        onCreate(db);
    }

    /**
     * Insert User to database table
     *
     * @param u User
     * @return
     */
    public Boolean insertUser(User u) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_UNAME, u.getUsername());
        cv.put(USER_PASSWORD, u.getPassword());
        cv.put(USER_EMAIL, u.getEmail());
        long result = db.insert(USER_TABLE, null, cv);
        return result > 0;
    }

    /**
     * Find user with username and password
     *
     * @param userName
     * @param password
     * @return null if user not found; user entity if user is present
     */
    public User findUser(String userName, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_UNAME + " = ? AND " + USER_PASSWORD + " = ?";
        cursor = db.rawQuery(query, new String[]{userName, password});
        if(cursor == null || cursor.getCount()<1){
            return null;
        }else{
            cursor.moveToFirst();
            User user = new User(cursor.getString(2),cursor.getString(3), cursor.getString(1));
            user.setId(cursor.getInt(0));
            return user;
        }
    }

    /**
     * Add a stock entity to table
     * @param stock
     * @return boolean - positive integer means saved, negative integer means failed to save.
     */
    public Boolean addStock(Stock stock) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STOCK_PK_ITEM_CODE, stock.getItemCode());
        cv.put(STOCK_ITEM_NAME, stock.getItemName());
        cv.put(STOCK_QTY, stock.getQtyStock());
        cv.put(STOCK_PRICE, stock.getPrice());
        cv.put(STOCK_TAXABLE, stock.getTaxable());
        long result = db.insert(STOCK_TABLE, null, cv);
        return result > 0;
    }

    /**
     * Delete Employee from Database Table
     *
     * @param id Employee Id for Employee to be deleted
     * @return
     */
   /* public int DeleteEmployee(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id=" + id, null);
    }*/

    /**
     * Get all Employees from Database
     *
     * @return data
     */
   /* public Cursor readEmployees() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        if (cursor.getCount() < 1) {
            cursor.moveToFirst();
        }
        return cursor;
    }*/
}
