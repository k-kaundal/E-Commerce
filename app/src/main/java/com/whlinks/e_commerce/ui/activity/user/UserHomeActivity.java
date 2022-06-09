package com.whlinks.e_commerce.ui.activity.user;

import static com.whlinks.e_commerce.R.id.allitems;
import static com.whlinks.e_commerce.R.id.cart;
import static com.whlinks.e_commerce.R.id.drawer;
import static com.whlinks.e_commerce.R.id.favorite;
import static com.whlinks.e_commerce.R.id.frame;
import static com.whlinks.e_commerce.R.id.home;
import static com.whlinks.e_commerce.R.id.latestitems;
import static com.whlinks.e_commerce.R.id.logout;
import static com.whlinks.e_commerce.R.id.navigation;
import static com.whlinks.e_commerce.R.id.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.whlinks.e_commerce.R;
import com.whlinks.e_commerce.ui.auth.LoginActivity;
import com.whlinks.e_commerce.ui.fragments.user.AllItemFragment;
import com.whlinks.e_commerce.ui.fragments.user.CartFragment;
import com.whlinks.e_commerce.ui.fragments.user.FavoriteItemsFragment;
import com.whlinks.e_commerce.ui.fragments.user.HomeFragment;
import com.whlinks.e_commerce.ui.fragments.LatestItemFragment;
import com.whlinks.e_commerce.ui.fragments.user.ProfileFragment;

import java.util.Objects;

public class UserHomeActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onStart() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(UserHomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.whlinks.e_commerce.R.layout.activity_user_home);
        frameLayout = findViewById(frame);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(drawer);
        setSupportActionBar(toolbar);
        navigationView = findViewById(navigation);
        actionBarDrawerToggle = new ActionBarDrawerToggle(UserHomeActivity.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().
                    beginTransaction().replace(frame, new HomeFragment()).commit();
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                switch (item.getItemId()) {
                    case home:
                        setTitle("Home");
                        fragmentManager.
                                beginTransaction().replace(frame, new HomeFragment()).commit();
                        break;
                    case latestitems:
                        setTitle("Latest Items");
                        fragmentManager.
                                beginTransaction().replace(frame, new LatestItemFragment()).commit();
                        break;
                    case cart:
                        setTitle("My Cart");
                        fragmentManager.
                                beginTransaction().replace(frame, new CartFragment()).commit();
                        break;
                    case favorite:
                        setTitle("Favorite Items");
                        fragmentManager.
                                beginTransaction().replace(frame, new FavoriteItemsFragment()).commit();
                        break;

                    case profile:
                        setTitle("Profile");
                        fragmentManager.
                                beginTransaction().replace(frame, new ProfileFragment()).commit();
                        break;
                    case logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(UserHomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    default:
                        fragmentManager.
                                beginTransaction().replace(frame, new HomeFragment()).commit();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }
}