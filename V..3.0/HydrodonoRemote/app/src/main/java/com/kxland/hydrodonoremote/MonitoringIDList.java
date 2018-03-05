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

public class MonitoringIDList extends ArrayAdapter<MonitoringID>{

    private Activity context;
    private List<MonitoringID> monitoringid;

    public MonitoringIDList(Activity context, List<MonitoringID> monitoringid){
        super(context, R.layout.list_layout, monitoringid);
        this.context = context;
        this.monitoringid = monitoringid;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout_controlling, null, true);

        TextView textViewId = (TextView) listViewItem.findViewById(R.id.textViewId);
        //TextView textViewDiffTime = (TextView) listViewItem.findViewById(R.id.textViewDiffTime);

        MonitoringID monitoringid = this.monitoringid.get(position);

        textViewId.setText(monitoringid.getIdArduino());

        //textViewDiffTime.setText(controling.getDiffTime());
//        textViewStatus.setText(controling.getStatus());
        return listViewItem;
    }

}