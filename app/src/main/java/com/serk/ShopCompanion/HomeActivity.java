package com.serk.ShopCompanion;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Azap Serkan, ie04114
 * Cette activité est la page principale de l'application lorsqu'il y a un utilisateur de connecter
 */

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;

    UserSharedPreferences userSharedPreferences;

    /**
     * Appelé lorsque l'activité démarre
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        userSharedPreferences = new UserSharedPreferences(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, new SearchFragment());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Recherche");
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        displayUserDetails();
    }


    /**
     * Appeler lorsque l'utilisateur appuis sur la touche retour
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Affiche les details de l'utilisateur connecté
     */
    private void displayUserDetails() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View drawerHeader = navigationView.inflateHeaderView(R.layout.main_nav_header);
        TextView userName = (TextView) drawerHeader.findViewById(R.id.userName);
        TextView userMail = (TextView) drawerHeader.findViewById(R.id.userMail);
        UserObject user = userSharedPreferences.getLoggedInUser();
        userName.setText(user.name);
        userMail.setText(user.mail);
    }

    /**
     * Appeler lorque un item du navigation drawer est selectioné
     *
     * @param item l'element choisi
     * @return true pour afficher l'element choisi comme choisi
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) { //Gère la selection du menu
        switch (item.getItemId()) {
            case R.id.search: //Choix de la recherche
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new SearchFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Recherche");
                item.setChecked(true);
                break;
            case R.id.listes: //Choix des listes de courses
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new ListesListFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Mes listes d'achat");
                item.setChecked(true);
                break;
            case R.id.cartes: //Choix des cartes de fidélité
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new LoyaltyCardListFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Mes cartes de fidélité");
                item.setChecked(true);
                break;
            case R.id.scanner: //Choix du scnanner
                Intent intent = new Intent(this, ScannedProductScannerActivity.class);
                startActivity(intent);
                break;
            case R.id.map: //Choix de la carte
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new MapFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Magasins à proximité");
                item.setChecked(true);
                break;
            case R.id.usersettings: //Choix des paramètres
                startActivity(new Intent(this, UserSettingActivity.class));
                break;
            case R.id.logout: //Deconnexion
                userSharedPreferences.clearUserData();
                userSharedPreferences.setUserLoggedIn(false);
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
