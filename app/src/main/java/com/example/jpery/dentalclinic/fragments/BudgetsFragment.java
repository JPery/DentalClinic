package com.example.jpery.dentalclinic.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jpery.dentalclinic.R;
import com.example.jpery.dentalclinic.utils.Constants;


public class BudgetsFragment extends Fragment {

    public BudgetsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_budgets, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_budgets_fragment);
        ListView list;
        list = (ListView) v.findViewById(R.id.budgets_list);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.interventions_array, android.R.layout.simple_list_item_1);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Fragment fragment = new BudgetInformationFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRAS_TITLE,adapter.getItem(position).toString());
                String description=null;
                switch (adapter.getItem(position).toString()){
                    case "Filling":
                        description = getString(R.string.filling_description);
                        break;
                    case "Endodontics":
                        description = getString(R.string.endodontics_description);
                        break;
                    case "Cleaning":
                        description = getString(R.string.cleaning_description);
                        break;
                }
                bundle.putString(Constants.EXTRAS_DESCRIPTION,description);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment,"budgetsInfo");
                fragmentTransaction.addToBackStack("budgetsInfo");
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
            }
        });
        return v;
    }

}
