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


public class IncomesFragment extends Fragment {
    private ListView incomes;
    private IncomesAdapter incomesAdapter;
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

        Cursor c = dbController.getIncomes();
        incomesAdapter = new IncomesAdapter(getActivity(), c, true);
        incomes.setAdapter(incomesAdapter);
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
        View root =inflater.inflate(R.layout.fragment_incomes, container, false);

        incomes = (ListView)root.findViewById(R.id.listViewIncomes);
        incomes.setAdapter(incomesAdapter);

        Button button = (Button) root.findViewById(R.id.add_income);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(getActivity(), "Test button2", Toast.LENGTH_SHORT).show();
                InsertDataIncomesFragment insertDataIncomesFragment = new InsertDataIncomesFragment();


                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, insertDataIncomesFragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

        return root;
    }


}