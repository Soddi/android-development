package com.soddi.budgetapplication;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddIncomeFragment extends Fragment {

    private EditText date, amount, title;
    private DBController dbController;

    public AddIncomeFragment() {
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

        View view = inflater.inflate(R.layout.fragment_add_income, container, false);

        date = (EditText) view.findViewById(R.id.editText_incomeDate);
        amount = (EditText) view.findViewById(R.id.editText_incomeAmount);
        title = (EditText) view.findViewById(R.id.editText_incomeTitle);


        Button cancelIncome = (Button) view.findViewById(R.id.button_cancel_income);
        cancelIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
                IncomeFragment incomeFragment = new IncomeFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.layout_main, incomeFragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

        Button addIncome = (Button) view.findViewById(R.id.button_add_income);
        addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {

                String incomeDate = date.getText().toString();
                int incomeAmount = Integer.parseInt(amount.getText().toString());
                String incomeTitle = title.getText().toString();

                if(incomeAmount <= 0) {
                    Toast.makeText(getActivity(), "Only positive amounts is allowed!", Toast.LENGTH_SHORT).show();
                } else {
                    long id = dbController.createIncome(incomeDate, incomeAmount, incomeTitle);
                    Toast.makeText(getActivity(), "Income with id " + id + " was created", Toast.LENGTH_SHORT).show();
                }

                IncomeFragment incomeFragment = new IncomeFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.layout_main, incomeFragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

        return view;
    }
}
