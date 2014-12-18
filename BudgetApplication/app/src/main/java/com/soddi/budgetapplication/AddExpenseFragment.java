package com.soddi.budgetapplication;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddExpenseFragment extends Fragment {

    private EditText date, amount, title;
    private DBController dbController;

    public AddExpenseFragment() {
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
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);

        date = (EditText) view.findViewById(R.id.editText_expenseDate);
        amount = (EditText) view.findViewById(R.id.editText_expenseAmount);
        title = (EditText) view.findViewById(R.id.editText_expenseTitle);

        Button cancelExpense = (Button) view.findViewById(R.id.button_cancel_expense);
        cancelExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
                ExpensesFragment expensesFragment = new ExpensesFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.layout_main, expensesFragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

        Button addExpense = (Button) view.findViewById(R.id.button_add_expense);
        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {

                String expenseDate = date.getText().toString();
                String expenseAmountStr = amount.getText().toString();
                int expenseAmount = Integer.parseInt(amount.getText().toString());
                String expenseTitle = title.getText().toString();

                if (expenseAmount >= 0) {
                    Toast.makeText(getActivity(), "Only negative amounts is allowed!", Toast.LENGTH_SHORT).show();
                } else {
                    Transaction transaction = new Transaction(expenseDate, expenseAmount, expenseTitle);
                    long id = dbController.createTransaction(transaction);
                    Toast.makeText(getActivity(), "Expense with " + id + " was created", Toast.LENGTH_SHORT).show();
                }

                ExpensesFragment expensesFragment = new ExpensesFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.layout_main, expensesFragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

        return view;
    }
}
