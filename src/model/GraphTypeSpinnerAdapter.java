package model;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by User on 15/04/2014.
 */
public class GraphTypeSpinnerAdapter extends ArrayAdapter<GraphType> {
    Context context;
    GraphType[] values;

    public GraphTypeSpinnerAdapter(Context context, int textViewResourceId, GraphType[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public GraphType getItem(int position) {
        return values[position];
    }

    @Override
    public long getItemId(int position) {
        return values[position].getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.WHITE);
        label.setText(values[position].getTypeName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.WHITE);
        label.setText(values[position].getTypeName());

        return label;
    }
}
