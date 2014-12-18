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


public class ExpensesFragment extends Fragment {

    private DBController dbController;
    private TransactionAdapter transactionAdapter;
    private ListView list_transactions;

    public ExpensesFragment() {
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

        Cursor c = dbController.getExpenses();
        transactionAdapter = new TransactionAdapter(getActivity(), c, true);
        list_transactions.setAdapter(transactionAdapter);
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
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);

        list_transactions = (ListView) view.findViewById(R.id.listView_Expenses);
        list_transactions.setAdapter(transactionAdapter);

        Button button = (Button) view.findViewById(R.id.button_Expenses);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                AddExpenseFragment addExpenseFragment = new AddExpenseFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.layout_main, addExpenseFragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

        return view;
    }

}
