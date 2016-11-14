package com.example.jpery.dentalclinic;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ArrangementsFragment.OnArrangementsLoadedListener {

    private static DrawerLayout drawer;
    private static NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_arrangements);
        Fragment fragment = new ArrangementsFragment();
        Bundle bundle = getIntent().getExtras();
        fragment.setArguments(bundle);
        showFragment(fragment);
    }

    private void showFragment(Fragment fragment){
        if (fragment!=null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            if(fragment instanceof ArrangementsFragment) {
                clearBackStack();
            }
            else{
                fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            getSupportActionBar().setTitle(R.string.title_activity_navigation_drawer);
            clearBackStack();
            navigationView.setCheckedItem(R.id.nav_arrangements);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_logout:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        switch (id){
            case R.id.nav_info:
                fragment = new InformationFragment();
                break;
            case R.id.nav_budget:
                fragment = new BudgetsFragment();
                break;
            case R.id.nav_arrangements:
                fragment = new ArrangementsFragment();
                break;
        }
        showFragment(fragment);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void clearBackStack() {
        getFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ArrangementsController.getInstance().getList().clear();
    }

    @Override
    public void showToast() {
        PreferenceManager.setDefaultValues(this,R.xml.preferences,false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = sharedPreferences.getString(SettingsFragment.KEY_PREF_USERNAME,"");
        if(sharedPreferences.getBoolean(SettingsFragment.KEY_PREF_WELCOME_MESSAGE,true)) {
            final TSnackbar topSnackbar = TSnackbar.make(findViewById(R.id.fragment_container), "Hi, " + username + " you have " + ArrangementsController.getInstance().getList().size() + " pending arrangements", TSnackbar.LENGTH_INDEFINITE).setActionTextColor(getResources().getColor(R.color.colorDivider));
            TextView textView = (TextView) topSnackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(getResources().getColor(R.color.colorIcons));
            topSnackbar.setAction("Dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    topSnackbar.dismiss();
                }
            });
            topSnackbar.show();
        }
    }
}
