package colintheshots.homelesshomescreen.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import colintheshots.homelesshomescreen.R;

/**
 * Created by colintheshots on 6/21/14.
 */
public class MainActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.testArray, R.layout.item_dropdown_spinner);


        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(mSpinnerAdapter, new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int i, long l) {
                Log.e("REMOVEME", "It worked!");
                return false;
            }
        });
    }

}
