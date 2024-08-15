package com.example.licentavarianta3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
public class MainActivity extends AppCompatActivity {

    private String userRole; // Variable to store the user role
    FloatingActionButton fab;
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Retrieve the user role from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userRole = sharedPreferences.getString("role", "user"); // Default to "user" if not found
        Log.d("USER ROLE", userRole);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fab = findViewById(R.id.fab);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        replaceFragment(new HomeFragment());

        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.shorts:
                    replaceFragment(new AboutUsFragment());
                    break;
                case R.id.subscriptions:
                    replaceFragment(new InfoFragment());
                    break;
                case R.id.library:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.listViewCalendar:
                    replaceFragment(new CalendarFragment());
                    break;
//                case R.id.viewRecomandari:
//                    replaceFragment(new RecomandariFragment());
//                    break;
                case R.id.viewRecomandariProprii:
                    replaceFragment(new RecomandariPropriiFragment());
                    break;
                case R.id.btnAdauga:
                    if (userRole.equals("admin")) {
                        replaceFragment(new AdaugaRecomandareFragment());
                    } else {
                        Toast.makeText(MainActivity.this, "Access denied. Only admins allowed.", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            return true;
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            switch (item.getItemId()) {
                case R.id.nav_home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.nav_about:
                    replaceFragment(new AboutUsFragment());
                    break;
                case R.id.nav_settings:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.nav_calendar:
                    replaceFragment(new CalendarFragment());
                    break;
                case R.id.nav_recomandari:
                    replaceFragment(new RecomandariFragment());
                    break;
                case R.id.nav_recomandari_proprii:
                    replaceFragment(new RecomandariPropriiFragment());
                    break;
                case R.id.nav_adauga_recomandare:
                    if (userRole.equals("admin")) {
                        replaceFragment(new AdaugaRecomandareFragment());
                    } else {
                        Toast.makeText(MainActivity.this, "Access denied. Only admins allowed.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.nav_logout:
                    logoutUser();
                    break;
                case R.id.nav_share:
                    Intent questionnaireIntent = new Intent(MainActivity.this, StimaDeSineActivity.class);
                    startActivity(questionnaireIntent);
                    break;
            }
            return true;
        });

        fab.setOnClickListener(view -> showBottomDialog());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        LinearLayout raspundeChestionar = dialog.findViewById(R.id.layoutRaspundeChestionar);
        LinearLayout informatiiGenerale = dialog.findViewById(R.id.layoutInformatiiGenerale);
        LinearLayout profilulTau = dialog.findViewById(R.id.layoutProfilulTau);
        //LinearLayout calendar = dialog.findViewById(R.id.listViewCalendar);
        //LinearLayout recomandari = dialog.findViewById(R.id.viewRecomandari);
        //LinearLayout recomandariProprii = dialog.findViewById(R.id.viewRecomandariProprii);
        //LinearLayout addRecomandare = dialog.findViewById(R.id.btnAdauga);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        raspundeChestionar.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(MainActivity.this, StimaDeSineActivity.class);
            startActivity(intent);
        });

        informatiiGenerale.setOnClickListener(v -> {
            dialog.dismiss();
            replaceFragment(new InfoFragment());
        });

        profilulTau.setOnClickListener(v -> {
            dialog.dismiss();
            replaceFragment(new ProfileFragment());
        });

        //am comentat eu
//        calendar.setOnClickListener(v -> {
//            dialog.dismiss();
//            replaceFragment(new CalendarFragment());
//        });//pana aici

//        recomandari.setOnClickListener(v -> {
//            dialog.dismiss();
//            replaceFragment(new RecomandariFragment());
//        });
//am comentat eu
//        recomandariProprii.setOnClickListener(v -> {
//            dialog.dismiss();
//            replaceFragment(new RecomandariPropriiFragment());
//        });
//
//        // Set visibility based on user role
//        if (userRole.equals("admin")) {
//            addRecomandare.setVisibility(View.VISIBLE);
//            addRecomandare.setOnClickListener(v -> {
//                dialog.dismiss();
//                replaceFragment(new AdaugaRecomandareFragment());
//            });
//        } else {
//            addRecomandare.setVisibility(View.GONE);
//        }
//pana aici
        cancelButton.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void logoutUser() {
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("userId");
        editor.remove("role");
        editor.apply();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
