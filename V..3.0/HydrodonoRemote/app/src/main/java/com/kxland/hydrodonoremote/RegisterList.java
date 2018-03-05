package com.kxland.hydrodonoremote;

import android.app.Activity;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Asus on 04/10/2017.
 */

    public class RegisterList extends ArrayAdapter<Controlling> {

        private Activity context;
        private List<Controlling> controling;

        public RegisterList(Activity context, List<Controlling> controling){
            super(context, R.layout.list_layout, controling);
            this.context = context;
            this.controling = controling;
        }

}
