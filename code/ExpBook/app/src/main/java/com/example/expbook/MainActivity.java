package com.example.expbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;
import android.app.AlertDialog;

public class MainActivity extends AppCompatActivity implements AddExpFragment.OnFragmentInteractionListener{
    // variable declaration
    private ArrayList<Expense> dataList; // to store expense objects
    private ListView expList; // to display expenses
    private ArrayAdapter<Expense> expAdapter; // to link data to listview
    private int editingPosition = -1; // to know the position of the item for editing, -1 if not editing
    private TextView summaryTotal; // to display the total amount spent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initializing the data list and setting up the listview and the adapter
        dataList = new ArrayList<>();
        expList = findViewById(R.id.expense_list);
        expAdapter = new CustomList(this, dataList);
        expList.setAdapter(expAdapter);
        // to open the fragment for adding expense
        final FloatingActionButton addButton = findViewById(R.id.add_expense_button);
        addButton.setOnClickListener(v -> {
            new AddExpFragment().show(getSupportFragmentManager(),"ADD_EXP");
        });
        // adding click listener to edit an item that has been clicked
        expList.setOnItemClickListener((adapterView, view, i, l) -> {
            Expense expense = dataList.get(i);
            editingPosition = i;
            Bundle bundle = new Bundle();
            bundle.putSerializable("expense", expense);
            bundle.putBoolean("isEdit", true);
            AddExpFragment fragment = new AddExpFragment();
            fragment.setArguments(bundle);
            fragment.show(getSupportFragmentManager(),"EDIT_EXP");
        });
        // to delete an item if it has been long clicked. Also, I have added setMessage to that it will ask for confirmation of deletion
        expList.setOnItemLongClickListener((adapterView, view, i, l) -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete Confirmation")
                    .setMessage("Are you sure you want to delete this expense?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dataList.remove(i);
                        expAdapter.notifyDataSetChanged();
                        updateTotal();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });

        summaryTotal = findViewById(R.id.summary);
    }

    // to calculate and display total charges
    private void updateTotal() {
        float total = 0;
        for (Expense expense : dataList) {
            try {
                total += Float.parseFloat(expense.getCharge());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        summaryTotal.setText(String.format(Locale.getDefault(), "Monthly Total: %.2f", total));
    }
    // if else in onOKPressed to check if it's for editing or adding an item
    @Override
    public void onOKPressed(Expense expense) {
        if (editingPosition != -1) {
            dataList.set(editingPosition, expense);
            editingPosition = -1;
        } else {
            dataList.add(expense);
        }
        expAdapter.notifyDataSetChanged();
        updateTotal();
    }
}