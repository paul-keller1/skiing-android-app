package com.example.skiing;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<MenuListItem> {
    final String TAG = "DEBUG";
    final String STR = "CustomAdapter";
    private LayoutInflater inflater;

    public CustomAdapter(Context context, int resourceId, List<MenuListItem> objects) {
        super(context, resourceId, objects);
        //resource = resourceId;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            Log.d("DEBUG","Inflating view");
            v = inflater.inflate(R.layout.menu_list_element, null);
            //v = inflater.inflate(resource, null);
        }

        MenuListItem i = getItem(position);

        Log.d(TAG,STR + "item i="+i);

        ImageView iconView;
        TextView textView;



        iconView = v.findViewById(R.id.elem_list_icon);
        textView = v.findViewById(R.id.elem_list_text);
        Log.d(TAG,STR + "nameTextView="+textView);

        try {
            iconView.setImageDrawable(i.getIcon());
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.d(TAG,STR + "NullPointerException in CustomAdapter while getting icon");
        }
        textView.setText(i.getText());

        return v;
    }
}
