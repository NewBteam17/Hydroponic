package com.kxland.hydrodonoremote;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;

public class MonitoringActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    EditText editTextPh;
    EditText editTextSuhu;
    EditText editTextEc;
    EditText editTextPupukA;
    EditText editTextPupukB;
    EditText editTextKetinggianWadah;
    EditText editTextIdArduino;
    EditText editTextTanggal;
    private  static final String TAG = "MonitoringActivity";
    Button buttonAdd;
    //Spinner spinnerGenres;

    DatabaseReference databaseMonitoring;

    //List<Monitoring> artists;

    ListView listViewMonitoring;

    List<Monitoring> monitoringList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle b = getIntent().getExtras();

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
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                //Menutup  drawer item klik
                drawerLayout.closeDrawers();
                //Memeriksa untuk melihat item yang akan dilklik dan melalukan aksi
                switch (menuItem.getItemId()){
                    // pilihan menu item navigasi akan menampilkan pesan toast klik kalian bisa menggantinya
                    //dengan intent activity
                    case R.id.navigation1:
                        Toast.makeText(getApplicationContext(), "Anda Masuk kehalaman Monitoring", Toast.LENGTH_SHORT).show();
                        Intent monitoring = new Intent(getApplicationContext(),MonitoringIDActivity.class);
                        startActivity(monitoring);
                        return true;
                    case R.id.navigation2:
                        Toast.makeText(getApplicationContext(), "Anda Masuk kehalaman Controlling", Toast.LENGTH_SHORT).show();
                        Intent controlling = new Intent(getApplicationContext(),ControllingActivity.class);
                        startActivity(controlling);
                        return true;
                    case R.id.navigation4:
                        Toast.makeText(getApplicationContext(),"Weather telah dipilih",Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(getApplicationContext(),WeatherActivity.class);
                        startActivity(in);
                        return true;
                    case R.id.navigation5:
                        Toast.makeText(getApplicationContext(),"Graphic telah dipilih",Toast.LENGTH_SHORT).show();
                        Intent gr = new Intent(getApplicationContext(),RegisterActivity.class);
                        startActivity(gr);
                        return true;
                    case R.id.navigation6:
                        Toast.makeText(getApplicationContext(),"Setting telah dipilih",Toast.LENGTH_SHORT).show();
                        Intent setting = new Intent(getApplicationContext(),SettingsActivity.class);
                        startActivity(setting);
                        return true;
                    case R.id.navigation7:
                        Toast.makeText(getApplicationContext(),"About telah dipilih",Toast.LENGTH_SHORT).show();
                        Intent abt = new Intent(getApplicationContext(),AboutActivity.class);
                        startActivity(abt);
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Kesalahan Terjadi ",Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
        // Menginisasi Drawer Layout dan ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){
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
        //Mensetting actionbarToggle untuk drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //memanggil synstate
        actionBarDrawerToggle.syncState();


        Button btnShowTOken = (Button)findViewById(R.id.button_show_token);
        btnShowTOken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get token
                String token = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG, "Token: " + token);
                Toast.makeText(MonitoringActivity.this,token, Toast.LENGTH_SHORT).show();

            }
        });



        databaseMonitoring = FirebaseDatabase.getInstance().getReference();

//        editTextPh = (EditText) findViewById(R.id.editTextPh);
//        editTextSuhu = (EditText) findViewById(R.id.editTextSuhu);
//        editTextEc = (EditText) findViewById(R.id.editTextEc);
//        editTextPupukA = (EditText) findViewById(R.id.editTextPupukA);
//        editTextPupukB = (EditText) findViewById(R.id.editTextPupukB);
//        editTextKetinggianWadah = (EditText) findViewById(R.id.editTextKetinggianWadah);
//        buttonAdd = (Button)findViewById(R.id.buttonAddArtist);
        //spinnerGenres = (Spinner)findViewById(R.id.spinnerGenres);

        listViewMonitoring = (ListView)findViewById(R.id.listViewArtists);

        monitoringList = new ArrayList<>();
//        buttonAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addMonitoring();
//            }
//        });


        listViewMonitoring.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Monitoring monitoring = monitoringList.get(i);

                showUpdateDialog(monitoring.getId(), monitoring.getPh());
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //Query recentPostsQuery = databaseMonitoring.limitToLast(1);

        Bundle b = getIntent().getExtras();

        String get_id = b.getString("idarduino");
        //String get_mulai = b.getString("mulai");
        //String get_akhir = b.getString("akhir");


        //Query recentPostsQuery = databaseMonitoring.child("pantau").child(get_id).orderByKey().startAt(get_mulai).endAt(get_akhir);
        Query recentPostsQuery = databaseMonitoring.child("pantau").child(get_id).orderByKey().limitToLast(6);

        recentPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                monitoringList.clear();

                for (DataSnapshot monitoringSnapshot  : dataSnapshot.getChildren()){
                    Monitoring monitoring = monitoringSnapshot.getValue(Monitoring.class);

                    monitoringList.add(monitoring);
                }

                MonitoringList adapter = new MonitoringList(MonitoringActivity.this, monitoringList);
                listViewMonitoring.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDialog(final String id, String ph) {

        AlertDialog.Builder dialogBuilder = new  AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog, null);

        dialogBuilder.setView(dialogView);

        final  EditText editTextPh = (EditText) dialogView.findViewById(R.id.editTextPh);
        final  EditText editTextSuhu = (EditText) dialogView.findViewById(R.id.editTextSuhu);
        final  EditText editTextEc = (EditText) dialogView.findViewById(R.id.editTextEc);
        final  EditText editTextPupukA = (EditText) dialogView.findViewById(R.id.editTextPupukA);
        final  EditText editTextPupukB= (EditText) dialogView.findViewById(R.id.editTextPupukB);
//        final  EditText editTextKetinggianWadah= (EditText) dialogView.findViewById(R.id.editTextKetinggianWadah);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        //final Spinner spinnerGenres = (Spinner) dialogView.findViewById(R.id.spinnerGenres);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);

        dialogBuilder.setTitle("Updating Monitoring"+id);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ph = editTextPh.getText().toString().trim();
                String suhu = editTextSuhu.getText().toString().trim();
                //String ec = spinnerGenres.getSelectedItem().toString();
                String ec = editTextEc.getText().toString().trim();
                String pupukA = editTextPupukA.getText().toString().trim();
                String pupukB = editTextPupukB.getText().toString().trim();
                String ketinggianWadah = editTextKetinggianWadah.getText().toString().trim();
                String idArduino = editTextIdArduino.getText().toString().trim();
                String tanggal = editTextTanggal.getText().toString().trim();

                if(TextUtils.isEmpty(ph)){
                    editTextPh.setError("Ph Required");
                    return;
                }
                if(TextUtils.isEmpty(suhu)){
                    editTextPh.setError("Suhu Required");
                    return;
                }

                updateMonitoring(id, ph, suhu, ec, pupukA, pupukB, ketinggianWadah, idArduino, tanggal);

                alertDialog.dismiss();


            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMonitoring(id);

            }
        });

    }


    private void deleteMonitoring(String id) {
        DatabaseReference drMonitoring = FirebaseDatabase.getInstance().getReference("monitoring").child(id);
        drMonitoring.removeValue();
        Toast.makeText(this, "Monitoring is deleted", Toast.LENGTH_LONG).show();
    }

    private boolean updateMonitoring(String id, String ph,String suhu, String ec, String pupukA, String pupukB, String ketinggianWadah, String idArduino, String tanggal){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("monitoring").child(id);

        Monitoring monitoring = new Monitoring(id, ph, suhu, ec, pupukA, pupukB, ketinggianWadah, idArduino, tanggal);

        databaseReference.setValue(monitoring);

        Toast.makeText(this, "Monitoring Update Succesfully", Toast.LENGTH_LONG).show();

        return true;

    }

    private void addMonitoring(){
        String ph = editTextPh.getText().toString().trim();
        String suhu = editTextSuhu.getText().toString().trim();
        String ec = editTextEc.getText().toString().trim();
        String pupukA = editTextPupukA.getText().toString().trim();
        String pupukB = editTextPupukB.getText().toString().trim();
        String ketinggianWadah = editTextKetinggianWadah.getText().toString().trim();
        String idArduino = editTextIdArduino.getText().toString().trim();
        String tanggal = editTextTanggal.getText().toString().trim();
//        String ec = spinnerGenres.getSelectedItem().toString();

        if (!TextUtils.isEmpty(ph)) {

            String id = databaseMonitoring.push().getKey();

            Monitoring monitoring = new Monitoring(id, ph,suhu, ec, pupukA, pupukB, ketinggianWadah, idArduino, tanggal);

            databaseMonitoring.child(id).setValue(monitoring);

            Toast.makeText(this,"Monitoring added", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this, "You should enter a ph", Toast.LENGTH_SHORT).show();
        }
    }
}