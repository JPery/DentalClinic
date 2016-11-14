package com.example.jpery.dentalclinic;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class AddArrangementActivity extends AppCompatActivity {

    // 7 days in milliseconds - 7 * 24 * 60 * 60 * 1000

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);
        Fragment fragment = new CreateArrangementFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }
}
