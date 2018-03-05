package com.kxland.hydrodonoremote;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MonitoringList extends ArrayAdapter<Monitoring>{

    private Activity context;
    private List<Monitoring> monitoring;

    public MonitoringList(Activity context, List<Monitoring> monitoring){
        super(context, R.layout.list_layout, monitoring);
        this.context = context;
        this.monitoring = monitoring;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewPh = (TextView) listViewItem.findViewById(R.id.textViewPh);
        TextView textViewSuhu = (TextView) listViewItem.findViewById(R.id.textViewSuhu);
        TextView textViewEc = (TextView) listViewItem.findViewById(R.id.textViewEc);
        TextView textViewPupukA = (TextView) listViewItem.findViewById(R.id.textViewPupukA);
        TextView textViewPupukB = (TextView) listViewItem.findViewById(R.id.textViewPupukB);
        TextView textViewKetinggianWadah = (TextView) listViewItem.findViewById(R.id.textViewKetinggianWadah);
        TextView textViewIdArduino = (TextView) listViewItem.findViewById(R.id.textViewIdArduino);
        TextView textViewTanggal = (TextView) listViewItem.findViewById(R.id.textViewTanggal);

        Monitoring monitoring = this.monitoring.get(position);

        textViewPh.setText(monitoring.getPh());
        textViewSuhu.setText(monitoring.getSuhu()+" °C");
        textViewEc.setText(monitoring.getEc());
        textViewPupukA.setText(monitoring.getPupukA()+" cm");
        textViewPupukB.setText(monitoring.getPupukB()+" cm");
        textViewKetinggianWadah.setText(monitoring.getKetinggianWadah()+" cm");
        textViewIdArduino.setText(monitoring.getIdArduino());
        textViewTanggal.setText(monitoring.getTanggal());

        return listViewItem;



    }

}
