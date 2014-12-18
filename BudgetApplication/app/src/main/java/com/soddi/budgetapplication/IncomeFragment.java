package com.soddi.budgetapplication;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class IncomeFragment extends Fragment {

    private DBController dbController;
    private ListView list_transactions;
    private IncomeAdapter incomeAdapter;

    public IncomeFragment() {
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

        Cursor c = dbController.getIncomes();
        incomeAdapter = new IncomeAdapter(getActivity(), c, true);
        list_transactions.setAdapter(incomeAdapter);
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

        View view = inflater.inflate(R.layout.fragment_income, container, false);

        list_transactions = (ListView) view.findViewById(R.id.listView_Income);
        list_transactions.setAdapter(incomeAdapter);

        Button button = (Button) view.findViewById(R.id.button_Income);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                AddIncomeFragment addIncomeFragment = new AddIncomeFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.layout_main, addIncomeFragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

        return view;
    }

}
