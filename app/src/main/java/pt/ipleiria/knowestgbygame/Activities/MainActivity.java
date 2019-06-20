package pt.ipleiria.knowestgbygame.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import pt.ipleiria.knowestgbygame.Fragments.ChallengeFragment;
import pt.ipleiria.knowestgbygame.Fragments.ClassificationFragment;
import pt.ipleiria.knowestgbygame.Fragments.DashboardFragment;
import pt.ipleiria.knowestgbygame.Fragments.GameFragment;
import pt.ipleiria.knowestgbygame.Fragments.MapFragment;
import pt.ipleiria.knowestgbygame.Fragments.ProfileFragment;
import pt.ipleiria.knowestgbygame.Fragments.SupportFragment;
import pt.ipleiria.knowestgbygame.R;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_dashboard);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
                break;
            case R.id.nav_classification:
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ClassificationFragment()).commit();
                break;
            case R.id.nav_map:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
            case R.id.nav_support:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SupportFragment()).commit();
                break;
            case R.id.nav_game:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GameFragment()).commit();
                break;
            case R.id.nav_challenge:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChallengeFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
