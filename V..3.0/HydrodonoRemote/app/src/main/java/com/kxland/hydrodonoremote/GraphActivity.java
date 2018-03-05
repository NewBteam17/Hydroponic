package com.kxland.hydrodonoremote;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    DatabaseReference databaseMonitoring;
    List<Monitoring> monitoringList;
    ListView listViewMonitoring;
    LineChart lineChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // Menginisiasi Toolbar dan mensetting sebagai actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Menginisiasi  NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        //Mengatur Navigasi View Item yang akan dipanggil untuk menangani item klik menu navigasi
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Memeriksa apakah item tersebut dalam keadaan dicek  atau tidak,
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                //Menutup  drawer item klik
                drawerLayout.closeDrawers();
                //Memeriksa untuk melihat item yang akan dilklik dan melalukan aksi
                switch (menuItem.getItemId()) {
                    // pilihan menu item navigasi akan menampilkan pesan toast klik kalian bisa menggantinya
                    //dengan intent activity
                    case R.id.navigation1:
                        Toast.makeText(getApplicationContext(), "Anda Masuk kehalaman Monitoring", Toast.LENGTH_SHORT).show();
                        Intent monitoring = new Intent(getApplicationContext(), MonitoringIDActivity.class);
                        startActivity(monitoring);
                        return true;
                    case R.id.navigation2:
                        Toast.makeText(getApplicationContext(), "Anda Masuk kehalaman Controlling", Toast.LENGTH_SHORT).show();
                        Intent controlling = new Intent(getApplicationContext(), ControllingActivity.class);
                        startActivity(controlling);
                        return true;
                    case R.id.navigation4:
                        Toast.makeText(getApplicationContext(), "Weather telah dipilih", Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(getApplicationContext(), WeatherActivity.class);
                        startActivity(in);
                        return true;
                    case R.id.navigation5:
                        Toast.makeText(getApplicationContext(), "Graphic telah dipilih", Toast.LENGTH_SHORT).show();
                        Intent gr = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(gr);
                        return true;
                    case R.id.navigation6:
                        Toast.makeText(getApplicationContext(), "Setting telah dipilih", Toast.LENGTH_SHORT).show();
                        Intent setting = new Intent(getApplicationContext(), SettingsActivity.class);
                        startActivity(setting);
                        return true;
                    case R.id.navigation7:
                        Toast.makeText(getApplicationContext(), "About telah dipilih", Toast.LENGTH_SHORT).show();
                        Intent abt = new Intent(getApplicationContext(),AboutActivity.class);
                        startActivity(abt);
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Kesalahan Terjadi ", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
        // Menginisasi Drawer Layout dan ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Kode di sini akan merespons setelah drawer menutup disini kita biarkan kosong
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //  Kode di sini akan merespons setelah drawer terbuka disini kita biarkan kosong
                super.onDrawerOpened(drawerView);
            }
        };

        databaseMonitoring = FirebaseDatabase.getInstance().getReference();
        //Mensetting actionbarToggle untuk drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //memanggil synstate
        actionBarDrawerToggle.syncState();

        lineChart = (LineChart) findViewById(R.id.chart);
        final Spinner spinnerFilter = (Spinner) findViewById(R.id.spinnerFilter);

        databaseMonitoring = FirebaseDatabase.getInstance().getReference();

        Bundle b = getIntent().getExtras();


        String get_id = b.getString("idarduino");
        //DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference().child("user-specialita").child(getUid()).child(mPostKey).child("content");
        final Query recentPostsQuery = databaseMonitoring.child("pantau").child(get_id).orderByKey().limitToLast(6);



        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                final String filter = spinnerFilter.getSelectedItem().toString();
                Toast.makeText(GraphActivity.this, filter,
                        Toast.LENGTH_SHORT).show();

                recentPostsQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        //TODO: disini
                        ArrayList<String> xAXES = new ArrayList<>();
                        ArrayList<Entry> yAXESph = new ArrayList<>();

                        int i = 0;
                        float fval = 0;

                        for (DataSnapshot monitoringSnapshot : dataSnapshot.getChildren()) {
                            Monitoring monitoring = monitoringSnapshot.getValue(Monitoring.class);

                            switch (filter) {
                                case "pH":
                                    fval = Float.valueOf(monitoring.getPh());
                                    break;
                                case "EC":
                                    fval = Float.valueOf(monitoring.getEc());
                                    break;
                                case "Suhu":
                                    fval = Float.valueOf(monitoring.getSuhu());
                                    break;
                                case "Ketinggian Pupuk A":
                                    fval = Float.valueOf(monitoring.getPupukA());
                                    break;
                                case "Ketinggian Pupuk B":
                                    fval = Float.valueOf(monitoring.getPupukB());
                                    break;
                                case "Ketinggian Wadah":
                                    fval = Float.valueOf(monitoring.getKetinggianWadah());
                                    break;

                            }


                            // Log.d("phval",String.valueOf(fval));
                            // Log.d("phtgl",monitoringSnapshot.getKey());
                            yAXESph.add(new Entry(fval,i)); //data ph
                            lineChart.notifyDataSetChanged();
                            lineChart.invalidate();

                            String a = monitoringSnapshot.getKey().toString();
                            long dv = Long.valueOf(a)*1000;
                            Date df = new java.util.Date(dv);
                            String vv = new SimpleDateFormat("dd/MM/yyyy hh:mma").format(df);

                            xAXES.add(i, vv); //tanggal

                            i++;

                        }

                        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
                        switch (filter) {
                            case "pH" :
                                LineDataSet lineDataSet1 = new LineDataSet(yAXESph,"ph");
                                lineDataSet1.setDrawCircles(false);
                                lineDataSet1.setColor(Color.CYAN);
                                lineDataSet1.setFillColor(Color.CYAN);
                                lineDataSet1.setDrawFilled(true);


                                lineDataSets.add(lineDataSet1);

                                //lineDataSet1.setDrawCubic(true);
                                break;
                            case "EC" :
                                LineDataSet lineDataSet2 = new LineDataSet(yAXESph,"EC");
                                lineDataSet2.setDrawCircles(false);
                                lineDataSet2.setColor(Color.YELLOW);
                                lineDataSet2.setFillColor(Color.YELLOW);
                                lineDataSet2.setDrawFilled(true);
                                lineDataSets.add(lineDataSet2);
                                break;

                            case "Suhu" :
                                LineDataSet lineDataSet3 = new LineDataSet(yAXESph,"Suhu");
                                lineDataSet3.setDrawCircles(false);
                                lineDataSet3.setColor(Color.RED);
                                lineDataSet3.setFillColor(Color.RED);
                                lineDataSet3.setDrawFilled(true);
                                lineDataSets.add(lineDataSet3);
                                break;

                            case "Ketinggian Pupuk A" :
                                LineDataSet lineDataSet4 = new LineDataSet(yAXESph,"Pupuk A");
                                lineDataSet4.setDrawCircles(false);
                                lineDataSet4.setColor(Color.BLACK);
                                lineDataSet4.setFillColor(Color.BLACK);
                                lineDataSet4.setDrawFilled(true);
                                lineDataSets.add(lineDataSet4);
                                break;

                            case "Ketinggian Pupuk B" :
                                LineDataSet lineDataSet5 = new LineDataSet(yAXESph,"Pupuk B");
                                lineDataSet5.setDrawCircles(false);
                                lineDataSet5.setColor(Color.WHITE);
                                lineDataSet5.setFillColor(Color.WHITE);
                                lineDataSet5.setDrawFilled(true);
                                lineDataSets.add(lineDataSet5);
                                break;

                            case "Ketinggian Wadah" :
                                LineDataSet lineDataSet6 = new LineDataSet(yAXESph,"Pupuk A");
                                lineDataSet6.setDrawCircles(false);
                                lineDataSet6.setColor(Color.rgb(32,178,70));
                                lineDataSet6.setFillColor(Color.rgb(32,178,70));
                                lineDataSet6.setDrawFilled(true);
                                lineDataSets.add(lineDataSet6);
                                break;
                        }

                        lineChart.setData(new LineData(xAXES,lineDataSets));
                        lineChart.setVisibleXRangeMaximum(65f);
                    }



                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(GraphActivity.this, "error",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }
}

