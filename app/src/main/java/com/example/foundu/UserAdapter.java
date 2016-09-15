package com.example.foundu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.foundu.model.BaseModel;
import com.example.foundu.model.User;

import java.util.List;

/**
 * Created by duongthoai on 7/30/16.
 */

public class UserAdapter extends BaseAdapter {

    List<User> users;
    Context context;

    public UserAdapter(Context ctx, List<User> objects) {
        users = objects;
        context = ctx;
    }

    @Override
    public int getCount() {
        if(users == null) {
            return 0;
        }
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.user_item_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.name);
        textView.setText(users.get(position).getId());
        return rowView;
    }

}
