package colintheshots.homelesshomescreen.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import colintheshots.homelesshomescreen.R;
import colintheshots.homelesshomescreen.model.serializers.Category;

/**
 * Created by colintheshots on 6/21/14.
 */
public class HelpListAdapter extends BaseAdapter {

    private List<Category> categoryList;
    private Context context;

    public HelpListAdapter(ListMultimap<String, Category> helpListMap, Context context, Integer i) {
        String key = Iterables.get(helpListMap.keySet(), i);
        this.categoryList = helpListMap.get(key);
        this.context = context;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
            holder.resourceImage = (ImageView) row.findViewById(R.id.resource_image);
            holder.resourceName = (TextView) row.findViewById(R.id.resource_name);
            holder.resourceUrl = (TextView) row.findViewById(R.id.resource_url);

            row.setTag(holder);
        } else {
            holder = (ResourceHolder) row.getTag();
        }

        Category value = categoryList.get(position);

        if (value!=null) {
            Picasso.with(context).load(value.getImage_url()).resize(80, 80).centerCrop().into(holder.resourceImage);
            holder.resourceName.setText(value.getName());
            holder.resourceUrl.setText(value.getUrl());
        }

        return row;
    }

    public class ResourceHolder {
        ImageView resourceImage;
        TextView resourceName;
        TextView resourceUrl;
    }

}
