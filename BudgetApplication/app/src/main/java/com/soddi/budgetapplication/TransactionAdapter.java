package com.soddi.budgetapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by soddi on 2014-12-17.
 */
public class TransactionAdapter extends CursorAdapter{
    private Transaction transaction;
    public TransactionAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View root = LayoutInflater.from(context).inflate(R.layout.custom_list_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder();

        viewHolder.title = (TextView) root.findViewById(R.id.transaction_Title);
        viewHolder.amount = (TextView) root.findViewById(R.id.transaction_Amount);
        viewHolder.id = (TextView) root.findViewById(R.id.transaction_ID);
        viewHolder.date = (TextView) root.findViewById(R.id.transaction_Date);

        root.setTag(viewHolder);

        return root;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        holder.title.setText("Title: \t" + cursor.getString(1));
        holder.amount.setText("Amount: \t" + cursor.getString(2));
        holder.id.setText("id: \t" + cursor.getString(4));
        holder.date.setText("Date: \t" + cursor.getString(3));
    }

    private class ViewHolder {
        TextView title;
        TextView amount;
        TextView id;
        TextView date;
    }
}
