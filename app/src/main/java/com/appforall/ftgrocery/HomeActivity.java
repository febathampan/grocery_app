package com.appforall.ftgrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.appforall.ftgrocery.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding homeBinding;
    ActionBarDrawerToggle mToggle;
    SharedPreferences sharedPreferences;

    /**
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = homeBinding.getRoot();
        setContentView(view);

        init();
    }

    /**
     * Initialize variables and set listeners
     */
    public void init() {
        sharedPreferences = getSharedPreferences("login_details", Context.MODE_PRIVATE);
        homeBinding.txtWelcome.setText("Welcome " + sharedPreferences.getString("USER_NAME", null) + "!");

        mToggle = new ActionBarDrawerToggle(this, homeBinding.drawerLayout, homeBinding.materialToolbar,
                R.string.nav_open, R.string.nav_close);
        homeBinding.drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        setSupportActionBar(homeBinding.materialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setNavigationDrawer();
       // SetBottomNavigation();

    }

    /**
     * Set navigation bottom bar
     */
   /* private void SetBottomNavigation() {
        homeBinding.bottomNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_bottom_search) {

                } else if (item.getItemId() == R.id.nav_bottom_profile) {

                } else if (item.getItemId() == R.id.nav_bottom_account) {

                }
                return false;
            }
        });
    }*/

    /**
     * Set navigation drawer for add, delete , list employees
     */
    private void setNavigationDrawer() {
        homeBinding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                int itemId = item.getItemId();
                if (itemId == R.id.nav_add_stock) {
                    fragment = new AddStockFragment();
                }else if (itemId == R.id.nav_sales) {
                    fragment = new SalesFragment();
                }else if (itemId == R.id.nav_purchase) {
                    fragment = new PurchaseFragment();
                }else if (itemId == R.id.nav_search_stock) {
                   // fragment = ;
                }else if (itemId == R.id.nav_list_stock) {
                    fragment = new ListItemsFragment();
                }else if (itemId == R.id.nav_log_out) {
                   // fragment = ;
                }
                if (fragment != null) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragment);
                    fragmentTransaction.commit();
                    homeBinding.drawerLayout.closeDrawers();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * @param item The menu item that was selected.
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
