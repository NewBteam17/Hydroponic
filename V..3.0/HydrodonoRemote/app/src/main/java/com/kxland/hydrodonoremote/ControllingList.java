package com.kxland.hydrodonoremote;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kxland.hydrodonoremote.R;

import java.util.List;

public class ControllingList extends ArrayAdapter<Controlling>{

    private Activity context;
    private List<Controlling> controling;

    public ControllingList(Activity context, List<Controlling> controling){
        super(context, R.layout.list_layout, controling);
        this.context = context;
        this.controling = controling;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout_controlling, null, true);

        TextView textViewId = (TextView) listViewItem.findViewById(R.id.textViewId);
        //TextView textViewDiffTime = (TextView) listViewItem.findViewById(R.id.textViewDiffTime);

        Controlling controling = this.controling.get(position);

        textViewId.setText(controling.getStatus());

        //textViewDiffTime.setText(controling.getDiffTime());
//        textViewStatus.setText(controling.getStatus());
        return listViewItem;



    }

}
