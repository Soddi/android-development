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
import android.widget.Toast;

public class AddIncomeFragment extends Fragment {

    public AddIncomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_income, container, false);

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
                //TODO: Add income to database
                Toast.makeText(getActivity(), "Income added!", Toast.LENGTH_SHORT).show();
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
