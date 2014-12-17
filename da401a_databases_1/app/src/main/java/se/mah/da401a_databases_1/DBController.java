package se.mah.da401a_databases_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ksango on 01/12/14.
 */
public class DBController extends SQLiteOpenHelper {

  private static final String NAME = "persondatabase";
  private static final int VERSION = 1;

  private static final String CREATETABLE = "CREATE TABLE persons " +
      "(_id integer primary key autoincrement , " +
      "name text not null , " +
      "code text not null);";

  private SQLiteDatabase db;

  public DBController(Context context){
    super(context, NAME, null, VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATETABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }

  public void open(){
    db = getWritableDatabase();
  }

  public void close(){
    db.close();
  }

  public long addPerson(String name, String code){
    ContentValues values = new ContentValues();
    values.put("name", name);
    values.put("code", code);
    return db.insert("persons", null, values);
  }

  public Cursor getPeople(){
    return db.query(
        "persons",
        new String[]{"_id", "name", "code"},
        null,
        null,
        null,
        null,
        null);
  }
}
