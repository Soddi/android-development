package com.soddi.budgetapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by soddi on 2014-12-17.
 */
public class DBController extends SQLiteOpenHelper{

    private static final String DB_NAME = "TransactionDatabase";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Transaction";

    private static final String CREATE_TABLE = "CREATE TABLE Transaction " +
            "(_id integer primary key autoincrement, " +
            "date text not null, " +
            "amount text not null, " +
            "title integer not null);";

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

        values.put("Title", transaction.getTitle());
        values.put("Amount", transaction.getAmount());
        values.put("Date", transaction.getDate());

        return db.insert("Transaction", null, values);
    }

    public Cursor getIncomes() {
        return db.query("Transaction", new String[]{"_id", "Date", "Amount", "Title"},
                "Amount >= 0", null, null, null, null);
    }
    public Cursor getExpenses() {

        String testQuery = "SELECT (_id, title, amount, date) FROM Transaction\n" +
                "UNION SELECT (_id, title, -amount, date) FROM Transaction\n" +
                "ORDER BY timestamp";
        return db.rawQuery("SELECT (_id, Title, Amount, Date) FROM Transaction WHERE Amount < 0", null);
    }
}
