package pt.ipleiria.knowestgbygame.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import pt.ipleiria.knowestgbygame.Fragments.ChallengeFragment;
import pt.ipleiria.knowestgbygame.Fragments.ClassificationFragment;
import pt.ipleiria.knowestgbygame.Fragments.DashboardFragment;
import pt.ipleiria.knowestgbygame.Fragments.GameFragment;
import pt.ipleiria.knowestgbygame.Fragments.MapFragment;
import pt.ipleiria.knowestgbygame.Fragments.ProfileFragment;
import pt.ipleiria.knowestgbygame.Fragments.SupportFragment;
import pt.ipleiria.knowestgbygame.Models.SessionManager;
import pt.ipleiria.knowestgbygame.R;

public class DashboardActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseAuth mAuth;
    private TextView points, games_played, profileName, profileEmail;
    private ImageView img_profile;
    private View header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        header = navigationView.getHeaderView(0);

        profileEmail = header.findViewById(R.id.textView_email_address);
        profileName = header.findViewById(R.id.textView_profile_name);
        img_profile =  header.findViewById(R.id.img_profile);

        profileName.setText(SessionManager.manager().getUser().getName());
        profileName.setText(SessionManager.manager().getUser().getName());
        if (SessionManager.manager().getUser().getAvatar() != null){
            Glide.with(this)
                    .load(SessionManager.manager().getUser().getAvatar())
                    .into(img_profile);
        }

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
                case R.id.logout:
                    mAuth.signOut();
                    Intent activityIntent;
                    activityIntent = new Intent(this, LoginActivity.class);
                    startActivity(activityIntent);
                    finish();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
