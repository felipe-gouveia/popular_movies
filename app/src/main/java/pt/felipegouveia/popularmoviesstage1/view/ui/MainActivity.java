package pt.felipegouveia.popularmoviesstage1.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import pt.felipegouveia.popularmoviesstage1.R;
import pt.felipegouveia.popularmoviesstage1.model.MovieResponse;
import pt.felipegouveia.popularmoviesstage1.util.Navigator;

public class MainActivity extends AppCompatActivity implements MoviesMasterFragment.OnMovieSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Movies);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) { //display intro fragment
            Navigator.addFragment(R.id.main_container, new MoviesMasterFragment(), "MovieMasterFragment", this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                int stackHeight = getSupportFragmentManager().getBackStackEntryCount();
                if (stackHeight > 0){
                    getSupportFragmentManager().popBackStack();
                } else {
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMovieSelected(MovieResponse movie) {
        Intent intent = new Intent(this, MovieDetailsFragment.class);
        intent.putExtra("Movie", movie);
        MovieDetailsFragment moveDetails = new MovieDetailsFragment();
        moveDetails.setArguments(intent.getExtras());
        Navigator.replaceFragmentWithAnimation(R.id.main_container, moveDetails, "MovieDetailsFragment", this);
    }
}
