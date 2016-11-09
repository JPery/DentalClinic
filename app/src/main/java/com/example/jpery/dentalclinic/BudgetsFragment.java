package com.example.jpery.dentalclinic;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;


public class BudgetsFragment extends Fragment {

    private static ListView list;

    public BudgetsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_budgets, container, false);
        list = (ListView) v.findViewById(R.id.budgets_list);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.interventions_array, android.R.layout.simple_list_item_1);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Snackbar.make(getActivity().getCurrentFocus(),"Item "+adapter.getItem(position).toString()+" clicked",Snackbar.LENGTH_LONG).show();
            }
        });
        return v;
    }

}
