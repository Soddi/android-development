package com.soddi.budgetapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by soddi on 2014-12-18.
 */
public class ExpenseAdapter extends CursorAdapter {
    public ExpenseAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View root = LayoutInflater.from(context).inflate(R.layout.custom_list_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder();

        viewHolder.title = (TextView) root.findViewById(R.id.transaction_Title);
        viewHolder.amount = (TextView) root.findViewById(R.id.transaction_Amount);
        viewHolder.date = (TextView) root.findViewById(R.id.transaction_Date);
        viewHolder.id = (TextView) root.findViewById(R.id.transaction_ID);

        root.setTag(viewHolder);

        return root;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        holder.title.setText(cursor.getString(1));
        holder.amount.setText(cursor.getString(2) + " kr");
        holder.date.setText(cursor.getString(3));
        holder.id.setText("id: " + cursor.getString(0));
    }

    private class ViewHolder {
        TextView title;
        TextView amount;
        TextView date;
        TextView id;
    }
}
