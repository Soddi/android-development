package com.soddi.budgetapplication;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SummaryFragment extends Fragment {

    private DBController dbController;
    private TextView textview_totalIncome, textview_totalExpense, textview_summary;
    private int totalIncome, totalExpense, summary;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        textview_totalIncome = (TextView) view.findViewById(R.id.texViewIncome);
        textview_totalExpense = (TextView) view.findViewById(R.id.texViewExpense);
        textview_summary = (TextView) view.findViewById(R.id.texViewSummary);

        totalIncome = dbController.getTotalIncome();

        textview_totalIncome.setText(totalIncome);
        //textview_totalExpense.setText(dbController.getTotalExpenses());
        //textview_summary.setText(dbController.getTotalIncome() - dbController.getTotalExpenses());

        return view;
    }
}
