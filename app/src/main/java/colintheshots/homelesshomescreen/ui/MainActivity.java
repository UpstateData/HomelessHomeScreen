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

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import colintheshots.homelesshomescreen.R;
import colintheshots.homelesshomescreen.adapters.HelpListAdapter;
import colintheshots.homelesshomescreen.model.serializers.Category;
import colintheshots.homelesshomescreen.model.serializers.MultimapSerializer;

/**
 * Created by colintheshots on 6/21/14.
 */
public class MainActivity extends ActionBarActivity {

    ListMultimap<String, Category> menuMap = LinkedListMultimap.create();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Multimap.class, new MultimapSerializer()).create();

        StringBuilder json=new StringBuilder();

        try {
            InputStream inputStream = getAssets().open("multimap.json");

            BufferedReader in=
                    new BufferedReader(new InputStreamReader(inputStream));

            String rawJson;

            while ((rawJson=in.readLine()) != null) {
                json.append(rawJson);
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        menuMap = (ListMultimap<String,Category>) gson.fromJson(json.toString(), ListMultimap.class);

//        menuMap.put("Benefits","benefits.json");
//        menuMap.put("Health","health.json");
//        menuMap.put("Employment","employment.json");
//        menuMap.put("Veterans","veterans.json");
//        menuMap.put("Housing","housing.json");
//        menuMap.put("Food","food.json");
//        menuMap.put("Domestic Violence","violence.json");
//        menuMap.put("Legal","legal.json");
//        menuMap.put("Education","education.json");

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
                    } else if (Character.isDigit(url.charAt(0))) { // if it starts with "1", dial a phone number
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
