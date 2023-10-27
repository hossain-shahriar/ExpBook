package com.example.expbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddExpFragment extends DialogFragment {
    private EditText expName;
    private EditText monthName;
    private EditText chargeName;
    private EditText commentName;
    private OnFragmentInteractionListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }
    // interface for MainActivity
    public interface OnFragmentInteractionListener {
        void onOKPressed(Expense expense);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_exp_fragment_layout, null);
        expName = view.findViewById(R.id.exp_edit_text);
        monthName = view.findViewById(R.id.month_edit_text);
        chargeName = view.findViewById(R.id.charge_edit_text);
        commentName = view.findViewById(R.id.comment_edit_text);
        // to check if we are editing existing expense and populating fields
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getBoolean("isEdit")) {
            Expense expense = (Expense) bundle.getSerializable("expense");
            if (expense != null) {
                expName.setText(expense.getExpenditure());
                monthName.setText(expense.getMonth());
                chargeName.setText(expense.getCharge());
                commentName.setText(expense.getComment());
            }
        }
        // to build and return the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add/edit Expense")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String exp = expName.getText().toString();
                        String month = monthName.getText().toString();
                        String charge = chargeName.getText().toString();
                        String comment = commentName.getText().toString();
                        // expense name field should not be null
                        if (exp.isEmpty()) {
                            Toast.makeText(getActivity(), "Expense name cannot be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // monthly charge should be number(float), can't be string
                        try {
                            Float.parseFloat(charge);
                        } catch (NumberFormatException e) {
                            Toast.makeText(getContext(), "Invalid charge input, please enter a number.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // month created should be in right formal which is yyyy-mm
                        if (!month.matches("^\\d{4}-\\d{2}$")) {
                            Toast.makeText(getContext(), "Invalid month input, please use the yyyy-mm format.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        listener.onOKPressed(new Expense(exp, month, charge, comment));
                    }
                })
                .create();
    }
}