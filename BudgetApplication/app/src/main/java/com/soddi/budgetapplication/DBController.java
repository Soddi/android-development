package com.soddi.budgetapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;


/**
 * Created by soddi on 2014-12-17.
 */
public class DBController extends SQLiteOpenHelper{

    private static final String DB_NAME = "TransactionDatabase";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "transaction";

    private static final String CREATE_TABLE = "CREATE TABLE transaction " +
            "(_id integer primary key autoincrement, " +
            "title text not null, " +
            "amount integer not null, " +
            "date text not null);";
    private SQLiteDatabase db;

    public DBController(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // database is upgraded
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(db);
    }

    public void open() {
        db = getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long createTransaction(Transaction transactionObject) {
        ContentValues values = new ContentValues();

        values.put("title", transactionObject.getTitle());
        values.put("amount", transactionObject.getAmount());
        values.put("date", transactionObject.getDate());

        return db.insert("transaction", null, values);
    }

    public Cursor getIncomes() {
        Log.d("DBController", "query from (amount >= 0) is not working");
        return db.query(
                "transaction",
                new String[]{"_id", "title", "amount", "date"},
                "amount >= 0",
                null,
                null,
                null,
                null);
    }
    public Cursor getExpenses() {

        String testQuery = "SELECT (_id, title, amount, date) FROM transaction\n" +
                "UNION SELECT (_id, title, -amount, date) FROM Transaction\n" +
                "ORDER BY date";
        return db.rawQuery("SELECT (_id, title, amount, date) FROM transaction WHERE amount < 0;", null);
    }
}
