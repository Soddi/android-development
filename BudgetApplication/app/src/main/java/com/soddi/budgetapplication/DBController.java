package com.soddi.budgetapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by soddi on 2014-12-17.
 */
public class DBController extends SQLiteOpenHelper{

    private static final String DB_NAME = "transactiondatabase";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "transactions";

    private static final String CREATE_TABLE = "CREATE TABLE transactions " +
            "(_id integer primary key autoincrement, " +
            "title text not null, " +
            "amount int not null, " +
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

        String title = transactionObject.getTitle();
        int amount = transactionObject.getAmount();
        String date = transactionObject.getDate();

        values.put("title", title);
        values.put("amount", amount);
        values.put("date", date);

        return db.insert(TABLE_NAME, null, values);
    }

    public Cursor getIncomes() {
        Log.d("DBController", "query from (amount >= 0) is not working");
        return db.query(
                "transactions",
                new String[]{"_id", "title", "amount", "date"},
                "amount > 0", null, null, null, null);
    }
    public Cursor getExpenses() {

        return db.query(
                "transactions",
                new String[]{"_id", "title", "amount", "date"},
                "amount < 0", null, null, null, null);
        //String testQuery = "SELECT (_id, title, amount, date) FROM transaction\n" +
        //        "UNION SELECT (_id, title, -amount, date) FROM Transaction\n" +
        //        "ORDER BY date";
        //return db.rawQuery("SELECT (_id, title, amount, date) FROM transactions WHERE amount < 0;", null);
    }

    public int getTotalIncome() {

        Cursor c = db.query(
                "transactions",
                new String[]{"sum(amount)"},
                "amount > 0", null, null, null, null);
        c.moveToFirst();
        return c.getInt(0);
    }

    public int getTotalExpenses() {
        Cursor c = db.query(
                "transactions",
                new String[]{"sum(amount)"},
                "amount < 0", null, null, null, null);
        c.moveToFirst();
        return c.getInt(0);
    }
}
