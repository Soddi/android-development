package com.soddi.budgetapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar();

        //Start SummaryFragment
        FragmentManager fm= getFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        IncomeFragment incomeFragment = new IncomeFragment();
        //SummaryFragment summaryFragment = new SummaryFragment();
        ft.add(R.id.layout_main, incomeFragment);
        ft.commit();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Fragment newFragment;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Income) {
            newFragment= new IncomeFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.layout_main,newFragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
            return true;
        }
        else if (id == R.id.action_Expenses) {
            newFragment= new ExpensesFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.layout_main,newFragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
        else if (id == R.id.action_Summary) {
            newFragment= new SummaryFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.layout_main,newFragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }

        return super.onOptionsItemSelected(item);
    }
}
