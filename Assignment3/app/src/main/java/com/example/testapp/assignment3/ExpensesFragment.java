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
import android.widget.Toast;

/**
 * Created by Sebastian Olsson on 14-12-01.
 */
public class ExpensesFragment extends Fragment {

    private ListView expenses;
    private ExpensesAdapter expensesAdapter;
    private DBcontroller dbController;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbController = new DBcontroller(getActivity());

    }

    @Override
    public void onResume() {
        super.onResume();

        dbController.open();

        Cursor c = dbController.getExpenses();
        expensesAdapter = new ExpensesAdapter(getActivity(), c, true);
        expenses.setAdapter(expensesAdapter);
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
         View root =inflater.inflate(R.layout.fragment_expenses, container, false);

        expenses = (ListView)root.findViewById(R.id.listViewExpenses);
        expenses.setAdapter(expensesAdapter);

        Button button = (Button) root.findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Test button", Toast.LENGTH_SHORT).show();
                InsertDataFragment insertDataFragment= new InsertDataFragment();


                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container,insertDataFragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

        return root;
    }



}
