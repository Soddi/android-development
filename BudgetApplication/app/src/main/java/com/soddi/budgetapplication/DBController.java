package com.soddi.budgetapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by soddi on 2014-12-17.
 */
public class DBController extends SQLiteOpenHelper {

    private static final String DB_NAME = "transactiondatabase";
    private static final int DB_VERSION = 1;

    private static final String TABLE_INCOME = "incomes";
    private static final String TABLE_EXPENSE = "expenses";

    private static final String CREATE_TABLE_INCOME = "CREATE TABLE " + TABLE_INCOME +
            "(_id integer primary key autoincrement, " +
            "title text not null, " +
            "amount integer not null, " +
            "date text not null);";

    private static final String CREATE_TABLE_EXPENSE = "CREATE TABLE " + TABLE_EXPENSE +
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

        db.execSQL(CREATE_TABLE_INCOME);
        db.execSQL(CREATE_TABLE_EXPENSE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // database is upgraded
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE + ";");
        onCreate(db);
    }

    public void open() {
        db = getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long createIncome(String title, int amount, String date) {
        ContentValues values = new ContentValues();

        values.put("title", title);
        values.put("amount", amount);
        values.put("date", date);

        return db.insert(TABLE_INCOME, null, values);
    }

    public long createExpense(String title, int amount, String date) {
        ContentValues values = new ContentValues();

        values.put("title", title);
        values.put("amount", amount);
        values.put("date", date);

        return db.insert(TABLE_EXPENSE, null, values);
    }

    public Cursor getIncomes() {
        return db.query(
                TABLE_INCOME,
                new String[]{"_id", "title", "amount", "date"},
                null, null, null, null, null);
    }

    public Cursor getExpenses() {

        return db.query(
                TABLE_EXPENSE,
                new String[]{"_id", "title", "amount", "date"},
                null, null, null, null, null);
    }

    public int getTotalIncome() {

        Cursor c = db.query(
                TABLE_INCOME,
                new String[]{"sum(amount)"},
                null, null, null, null, null);
        c.moveToFirst();
        return c.getInt(0);
    }

    public int getTotalExpenses() {
        Cursor c = db.query(
                TABLE_EXPENSE,
                new String[]{"sum(amount)"},
                null, null, null, null, null);
        c.moveToFirst();
        return c.getInt(0);
    }
}
