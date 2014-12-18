package com.soddi.budgetapplication;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SummaryFragment extends Fragment {

    private DBController dbController;
    private TextView textview_totalIncome, textview_totalExpense, textview_summary;
    private int totalIncome, totalExpense, summary = 0;

    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbController = new DBController(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        dbController.open();
    }

    @Override
    public void onPause() {
        super.onPause();
        dbController.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        textview_totalIncome = (TextView) view.findViewById(R.id.texViewIncome);
        textview_totalExpense = (TextView) view.findViewById(R.id.texViewExpense);
        textview_summary = (TextView) view.findViewById(R.id.texViewSummary);

        if(dbController != null) {
            //totalIncome = dbController.getTotalIncome();
            Toast.makeText(getActivity(), dbController.getDatabaseName(), Toast.LENGTH_LONG).show();
            totalIncome = dbController.getTotalIncome();
        } else {
            Toast.makeText(getActivity(), "DBControllern är inte korrekt instansierad!", Toast.LENGTH_SHORT).show();
        }

        textview_totalIncome.setText(totalIncome + " kr");
        //textview_totalExpense.setText(dbController.getTotalExpenses() + " kr");
        //textview_summary.setText(dbController.getTotalIncome() - dbController.getTotalExpenses() + " kr");

        return view;
    }
}
