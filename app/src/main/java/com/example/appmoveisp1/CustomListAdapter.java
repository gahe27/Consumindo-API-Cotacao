package com.example.appmoveisp1;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appmoveisp1.ListItem;
import com.example.appmoveisp1.R;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<ListItem> {
    private Context context;
    private int resource;

    public CustomListAdapter(Context context, int resource, List<ListItem> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textView);

        ListItem item = getItem(position);

        if (item != null) {
            imageView.setImageResource(item.getImageResId());
            textView.setText(item.getText());
        }

        return convertView;
    }
}