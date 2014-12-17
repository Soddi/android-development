package com.example.testapp.assignment3;

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
import android.widget.TextView;
import android.widget.Toast;


public class SummaryFragment extends Fragment {
    private DBcontroller dbController;
    private TextView totalAmountIncomes, totalAmountExpenses, totalDiff;
    private int totalIncomes, totalExpenses, totalBudget;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbController = new DBcontroller(getActivity());



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

        View myInflater = inflater.inflate(R.layout.fragment_summary, container, false);

        totalAmountExpenses = (TextView)myInflater.findViewById(R.id.textView_sumExpenses);
        totalAmountIncomes= (TextView)myInflater.findViewById(R.id.textView_sumIncomes);
        totalDiff=(TextView)myInflater.findViewById(R.id.textView_sumDiff);

        Button button = (Button) myInflater.findViewById(R.id.button_summary);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           //     Toast.makeText(getActivity(), "Test button"+dbController.getExpensesAmount(), Toast.LENGTH_SHORT).show();
                totalAmountExpenses.setText("Expenses:\t"+dbController.getExpensesAmount());
                totalAmountIncomes.setText("Incomes:\t"+dbController.getIncomesAmount());
                int totalBudget = dbController.getIncomesAmount()-dbController.getExpensesAmount();
                totalDiff.setText("Total relation:\t"+totalBudget);

            }
        });

        return myInflater ;
    }


    @Override
    public void onPause() {
        super.onPause();
        dbController.close();
    }




}
