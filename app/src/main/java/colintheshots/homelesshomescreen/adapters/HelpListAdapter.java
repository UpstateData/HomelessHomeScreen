package colintheshots.homelesshomescreen.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.common.collect.Iterables;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import colintheshots.homelesshomescreen.R;

/**
 * Created by colintheshots on 6/21/14.
 */
public class HelpListAdapter extends BaseAdapter {

    private Map<String, String> helpListMap = new HashMap<String, String>();
    private Context context;

    public HelpListAdapter(Map<String, String> helpListMap, Context context, int i) {

        this.context = context;

        String key = Iterables.get(helpListMap.keySet(), i);
        StringBuilder json=new StringBuilder();

        try {
            Log.e("TEST", helpListMap.get(key));
            InputStream inputStream = context.getAssets().open(helpListMap.get(key));

            BufferedReader in=
                    new BufferedReader(new InputStreamReader(inputStream));

            String rawJson = "";

            while ((rawJson=in.readLine()) != null) {
                json.append(rawJson);
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String,String> map=new HashMap<String,String>();

        this.helpListMap = (Map<String,String>) new Gson().fromJson(json.toString(), map.getClass());
    }

    @Override
    public int getCount() {
        return helpListMap.size();
    }

    @Override
    public Object getItem(int position) {
        return Iterables.get(helpListMap.keySet(), position);
    }

    @Override
    public long getItemId(int position) {
        return 0L;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ResourceHolder holder;


        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.row_help_list, parent, false);
            assert row != null;

            holder = new ResourceHolder();
            holder.resourceName = (TextView) row.findViewById(R.id.resource_name);
            holder.resourceUrl = (TextView) row.findViewById(R.id.resource_url);

            row.setTag(holder);
        } else {
            holder = (ResourceHolder) row.getTag();
        }

        String key = (String) getItem(position);

        holder.resourceName.setText(key);
        holder.resourceUrl.setText(helpListMap.get(key));

        return row;
    }

    public class ResourceHolder {
        TextView resourceName;
        TextView resourceUrl;
    }

}
