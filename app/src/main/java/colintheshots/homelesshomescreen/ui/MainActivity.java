package colintheshots.homelesshomescreen.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import colintheshots.homelesshomescreen.R;
import colintheshots.homelesshomescreen.adapters.HelpListAdapter;

/**
 * Created by colintheshots on 6/21/14.
 */
public class MainActivity extends ActionBarActivity {

    Map<String, String> menuMap = new HashMap<String, String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        menuMap.put("Benefits","benefits.json");
        menuMap.put("Health","health.json");
        menuMap.put("Employment","employment.json");
        menuMap.put("Veterans","veterans.json");

        SpinnerAdapter mSpinnerAdapter = new ArrayAdapter<String>(this,
                R.layout.item_dropdown_spinner, menuMap.keySet().toArray(new String[menuMap.keySet().size()]));

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(mSpinnerAdapter, new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int i, long l) {
                HelpListAdapter helpListAdapter = new HelpListAdapter(menuMap, MainActivity.this, i);
                ListView listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(helpListAdapter);
                listView.setVisibility(View.VISIBLE);
                return false;
            }
        });

        ListView listView = (ListView) findViewById(R.id.listView);
        if (listView!=null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView resource_url = (TextView) view.findViewById(R.id.resource_url);
                    String url = resource_url.getText().toString();
                    if (url.startsWith("http")) { // load a browser if website
                        Intent viewIntent = new Intent(Intent.ACTION_VIEW);
                        viewIntent.setData(Uri.parse(url));
                        startActivity(viewIntent);
                    } else if (url.startsWith("1")) { // if it starts with "1", dial a phone number
                        String uri = "tel:" + url.trim();
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(uri));
                        startActivity(intent);
                    } else { // load the app if available
                        Intent packageIntent;
                        try {
                            packageIntent = getPackageManager().getLaunchIntentForPackage(url);
                            if (packageIntent == null) throw new PackageManager.NameNotFoundException();
                            packageIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                            startActivity(packageIntent);
                        } catch (PackageManager.NameNotFoundException e) { // if app is unavailable, go to Play Store
                            Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                            marketIntent.setData(Uri.parse("market://details?id=" + url));
                            startActivity(marketIntent);
                        }

                    }
                }
            });
        }
    }

}
