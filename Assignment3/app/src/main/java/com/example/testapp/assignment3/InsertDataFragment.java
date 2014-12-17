package com.example.testapp.assignment3;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class InsertDataFragment extends Fragment {

    private EditText date, title,amount;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_insert_data, container, false);

        amount = (EditText) root.findViewById(R.id.editText_Amount);
        date = (EditText) root.findViewById(R.id.editText_Date);
        title = (EditText) root.findViewById(R.id.editText_Title);

        Button button = (Button) root.findViewById(R.id.create_Expense);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newAmount = amount.getText().toString();
                String newDate = date.getText().toString();
                String newTitle = title.getText().toString();

                long id = dbController.dataIntoExpenses(newTitle,newAmount,newDate);
            //    Toast.makeText(getActivity(), "Person with id " + id + " was created", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onPause() {
        super.onPause();

        dbController.close();
    }
}
