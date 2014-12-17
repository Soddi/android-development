package com.example.testapp.assignment3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.sql.Date;

/**
 * Created by Sebastian Olsson on 14-12-01.
 * Controller that handles everything with SQLite
 */
public class DBcontroller extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static final String TAG ="Dbhelper" ;
    private static final String NAME = "COMPANY_DATABASE";
    private static final String TABLE_NAME_1 = "Incomes";
    private static final String TABLE_NAME_2 = "Expenses";
    private static final int VERSION =1;


    public DBcontroller(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Incomes"  +
                "(_id integer primary key autoincrement, " +
                "Date text not null, " +
                "Amount int not null,"+
                "Title text not null);");


        db.execSQL("CREATE TABLE Expenses"+
                "(_id integer primary key autoincrement, " +
                "Date text not null, " +
                "Amount int not null,"+
                "Title text not null);");
        //open();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,"Upgrading from version"+oldVersion+"to"+newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);

        onCreate(db);


    }

    public void open(){

        db = getWritableDatabase();
      // dataIntoExpenses();
    }

    public void close(){
        db.close();
    }

    public long dataIntoExpenses(String title,String amount,String date) {
        // Create a new map of values, where column names are the keys

        ContentValues values = new ContentValues();

        values.put("Title", title);
        values.put("Date",date);
        values.put("Amount", Integer.parseInt(amount));
        // Insert the new row, returning the primary key value of the new row
        return db.insert("Expenses",null,values);
    }


    public long dataIntoIncomes(String title,String amount,String date) {
        // Create a new map of values, where column names are the keys

        ContentValues values = new ContentValues();

        values.put("Title", title);
        values.put("Date",date);
        values.put("Amount", Integer.parseInt(amount));
        // Insert the new row, returning the primary key value of the new row
        return db.insert("Incomes",null,values);
    }

    public Cursor getExpenses() {
        return db.query(
                "Expenses",
                new String[]{"_id", "Date", "Amount", "Title"},
                null,
                null,
                null,
                null,
                null);

    }

    public Cursor getIncomes(){
        return db.query(
                "Incomes",
                new String[]{"_id", "Date", "Amount","Title"},
                null,
                null,
                null,
                null,
                null);
    }

    /*
    This method gets the summary of amount in Incomes table
    so the data can be presented in a graph
     */
    public int getIncomesAmount(){
        Cursor c = db.rawQuery("SELECT sum(Amount) as AmountSumIncomes FROM Incomes", null);
        int total = 0;

        if (c != null && c.moveToFirst());
        do {

            total=c.getInt(0);


        } while (c.moveToNext());

        return total;
    }

    /*
    This method gets the summary of amount in Expenses table
    so the data can be presented in a graph
     */
    public int getExpensesAmount(){
        Cursor c2 = db.rawQuery("SELECT sum(Amount) AS AmountSumExpenses FROM Expenses", null);
        int total = 0;

        if (c2 != null && c2.moveToFirst());
        do {

           total=c2.getInt(0);


        } while (c2.moveToNext());

        return total;

    }
}
