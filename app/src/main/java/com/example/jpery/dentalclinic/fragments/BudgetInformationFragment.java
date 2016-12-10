package com.example.jpery.dentalclinic.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jpery.dentalclinic.R;
import com.example.jpery.dentalclinic.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class BudgetInformationFragment extends Fragment {

    public BudgetInformationFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView budgetTitle;
        TextView budgetDescription;
        View v = inflater.inflate(R.layout.fragment_budget_information, container, false);
        budgetTitle = (TextView) v.findViewById(R.id.appointment_title);
        budgetDescription = (TextView) v.findViewById(R.id.budget_description);
        budgetTitle.setText(getArguments().getString(Constants.EXTRAS_KIND_OF_INTERVENTION));
        budgetDescription.setText(getArguments().getString(Constants.EXTRAS_DESCRIPTION));
        return v;
    }

}
