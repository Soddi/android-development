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

    private static final String DB_NAME = "TransactionDatabase";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Transaction";

    private static final String CREATE_TABLE = "CREATE TABLE transaction " +
            "(_id integer primary key autoincrement, " +
            "date text not null, " +
            "amount integer not null, " +
            "title text not null);";

    private static final String CREATETABLE = "CREATE TABLE persons " +
            "(_id integer primary key autoincrement , " +
            "name text not null , " +
            "code text not null);";

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

    public long createTransaction(Transaction transaction) {
        ContentValues values = new ContentValues();

        values.put("title", transaction.getTitle());
        values.put("amount", transaction.getAmount());
        values.put("date", transaction.getDate());

        return db.insert("transactions", null, values);
    }

    public Cursor getIncomes() {
        Log.d("DBController", "query from (amount >= 0) is not working");
        return db.query(
                "transactions",
                new String[]{"_id", "title", "amount", "date"},
                null,
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
