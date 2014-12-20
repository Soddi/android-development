package com.soddi.budgetapplication;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;

import java.util.ArrayList;

public class SummaryFragment extends Fragment {

    private DBController dbController;
    private TextView textview_totalIncome, textview_totalExpense, textview_summary;
    private int totalIncome, totalExpense, summary = 0;

    private PieChart chart;

    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbController = new DBController(getActivity());
        dbController.open();
    }

    @Override
    public void onResume() {
        super.onResume();
        dbController.open();
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

        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        chart = (PieChart) view.findViewById(R.id.chart);
        String summary = "Summary: " + (dbController.getTotalIncome() + dbController.getTotalExpenses()) + " Kr";


        //PieChart External library
        // change the color of the center-hole
        chart.setHoleColor(Color.rgb(235, 235, 235));
        chart.setHoleRadius(60f);
        chart.setDescription("");
        chart.setDrawYValues(true);
        chart.setDrawXValues(true);
        chart.setDrawCenterText(true);
        chart.setDrawHoleEnabled(true);
        chart.setRotation(0f);
        chart.setRotationEnabled(true);
        chart.setCenterText("Summary");
        setData(dbController.getTotalIncome(), dbController.getTotalExpenses());
        chart.animateXY(2000, 2000);

        Legend one = chart.getLegend();
        one.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        one.setXEntrySpace(7f);
        one.setYEntrySpace(5f);

        chart.setCenterText(summary);

        return view;
    }

    private void setData(int income, int expense) {

        ArrayList<Entry> yVals = new ArrayList<Entry>();
        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        yVals.add(new Entry((float) income, 0));
        yVals.add(new Entry((float) expense, 1));

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Incomes");
        xVals.add("Expenses");

        PieDataSet set1 = new PieDataSet(yVals, "Transactions");
        set1.setSliceSpace(3f);

        int darkRed = getResources().getColor(android.R.color.holo_red_dark);
        int darkGreen = getResources().getColor(android.R.color.holo_green_dark);
        set1.setColors(new int[] {darkGreen, darkRed});

        PieData data = new PieData(xVals, set1);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }

}
