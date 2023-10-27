package com.example.expbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<Expense> {
    private ArrayList<Expense> expenses;
    private Context context;
    public CustomList(Context context, ArrayList<Expense> expenses){
        super(context, 0, expenses);
        this.expenses = expenses;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content, parent, false);
        }
        Expense expense = expenses.get(position);
        TextView expName = view.findViewById(R.id.exp_name);
        TextView monthName = view.findViewById(R.id.month_started);
        TextView chargeName = view.findViewById(R.id.monthly_charge);
        TextView commentName = view.findViewById(R.id.comment);
        expName.setText(expense.getExpenditure());
        monthName.setText(expense.getMonth());
        chargeName.setText(expense.getCharge());
        commentName.setText(expense.getComment());
        return view;
    }
}