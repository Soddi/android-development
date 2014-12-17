package com.soddi.budgetapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by soddi on 2014-12-17.
 */
public class DBController extends SQLiteOpenHelper{

    private static final String NAME = "TransactionDatabase";
    private static final int VERSION = 1;

    private static final String TABLE_NAME = "Transaction";

    private static final String CREATETABLE = "CREATE TABLE Transactions " +
            "(_id integer primary key autoincrement, " +
            "Date text not null, " +
            "Amount int not null" +
            "Title text not null +" +
            "Type is not null)";

    private SQLiteDatabase db;

    public DBController(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATETABLE);
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

    public long addTransaction(Transaction transaction) {
        ContentValues values = new ContentValues();

        values.put("title", transaction.getTitle());
        values.put("amount", transaction.getAmount());
        values.put("date", transaction.getDate());

        return db.insert("Transaction", null, values);
    }

    public Cursor getIncomes() {
        return db.query("Transaction", new String[]{"_id", "date", "amount", "title"},
                "amount >= 0", null, null, null, null);
    }
    public Cursor getExpenses() {

        String query = "SELECT (_id, title, amount, date) FROM Transaction\n" +
                "UNION SELECT (_id, title, -amount, date) FROM Transaction\n" +
                "ORDER BY timestamp";
        return db.rawQuery(query, null);
    }
}
