package se.mah.da401a_databases_1;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by ksango on 01/12/14.
 */
public class PeopleAdapter extends CursorAdapter {

  public PeopleAdapter(Context context, Cursor c, boolean autoRequery) {
    super(context, c, autoRequery);
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    View root = LayoutInflater.from(context).inflate(R.layout.peoplerow, parent, false);

    ViewHolder holder = new ViewHolder();

    holder.name = (TextView) root.findViewById(R.id.peoplename);
    holder.code = (TextView) root.findViewById(R.id.paopelcode);

    root.setTag(holder);

    return root;
  }

  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    ViewHolder holder = (ViewHolder) view.getTag();

    holder.name.setText(cursor.getString(1));
    holder.code.setText(cursor.getString(2));
  }

  private class ViewHolder {
    TextView name;
    TextView code;
  }
}
