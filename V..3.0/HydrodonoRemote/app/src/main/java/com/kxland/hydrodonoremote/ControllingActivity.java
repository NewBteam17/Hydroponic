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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ServerValue;
import com.kxland.hydrodonoremote.MonitoringActivity;
import com.kxland.hydrodonoremote.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

public class ControllingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;


    private  static final String TAG = "ControllingActivity";

    DatabaseReference databaseControlling;
    ListView listViewControlling;
    List<Controlling> controllingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlling);

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
                    Toast.makeText(getApplicationContext(),"Register telah dipilih",Toast.LENGTH_SHORT).show();
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


//        Button btnShowTOken = (Button)findViewById(R.id.button_show_token);
//        btnShowTOken.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //get token
//                String token = FirebaseInstanceId.getInstance().getToken();
//                Log.d(TAG, "Token: " + token);
//                Toast.makeText(ControllingActivity.this,token, Toast.LENGTH_SHORT).show();
//
//            }
//        });



        databaseControlling = FirebaseDatabase.getInstance().getReference();

        listViewControlling = (ListView)findViewById(R.id.listViewArtists);


        controllingList = new ArrayList<>();


        listViewControlling.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Controlling controlling = controllingList.get(i);

                showUpdateDialog(controlling.getIdArduino(), controlling.getStatus());
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //get email dari uid (mobile+account)
        String strCurrentUser =  FirebaseAuth.getInstance().getCurrentUser().getEmail();

        //cari dari pengguna, memiliki device ARXX apa saja
        Query recentQueryPengguna = databaseControlling.child("pengguna").orderByChild("email").equalTo(strCurrentUser);

        final ControllingActivity mm = this;
        recentQueryPengguna.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){
                    Toast.makeText(mm, "Anda belum memiliki perangkat hidroponik yang terdaftar, hubungi admin", Toast.LENGTH_LONG).show();
                    return;
                }

                String gIdarduino = dataSnapshot.getChildren().iterator().next().child("idarduino").getValue().toString();

                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(gIdarduino);
                    String[] strArr = new String[jsonArray.length()];

                    controllingList.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        strArr[i] = jsonArray.getString(i);
                        Log.d(TAG, "User idarduino: " + strArr[i]);
                        //tampilkan di pilihan untuk link ke node kendali
                        //TODO: contoh ambil idarduino ke 1 (0)
                        //idaraktif = strArr[0];
                        Controlling controlling = new Controlling();
                        controlling.idarduino = strArr[i];
                        controlling.diffTime = "tezz1";
                        controlling.status = strArr[i];

                        controllingList.add(controlling);
                    }

                    ControllingList adapter = new ControllingList(ControllingActivity.this, controllingList);
                    listViewControlling.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void showUpdateDialog(final String idarduino, final String status) {

        AlertDialog.Builder dialogBuilder = new  AlertDialog.Builder(this);


        String id = databaseControlling.child("kendali").getKey();

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog_controlling, null);


        dialogBuilder.setView(dialogView);

        //textViewId.setText(controling.getStatus());

        //final  EditText editTextDiffTime = (EditText) dialogView.findViewById(R.id.editTextDiffTime);
        //final  EditText editTextStatus = (EditText) dialogView.findViewById(R.id.editTextStatus);
        final Spinner spinnerDiffTime = (Spinner) dialogView.findViewById(R.id.spinnerDiffTime);
        final Spinner spinnerStatus = (Spinner) dialogView.findViewById(R.id.spinnerStatus);
        final  Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);



//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//        String key = ref.push().getKey(); // this will create a new unique key
//        Map<String, Object> value = new HashMap<>();
//        value.put("name", "shesh");
//        value.put("address", "lucknow");
//        value.put("format waktu indo sekarang", "02-10-2017 17:14:00");
//        value.put("format timestamp server firebase", ServerValue.TIMESTAMP);
//        ref.child(key).setValue(value);
//
//        dialogBuilder.setTitle(value.toString());

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {

                //String difftime = editTextDiffTime.getText().toString().trim();
                //String difftime = spinnerDiffTime.getSelectedItem().toString();
                String status = spinnerStatus.getSelectedItem().toString();

                String difftime = spinnerDiffTime.getSelectedItem().toString() ;

                switch (difftime) {
                    case "3 Hari" :
                        String difftime2 = "259200";
                        updateControlling(idarduino, difftime2, status);
                        alertDialog.dismiss();
                        break;

                    case "4 Hari" :
                        difftime2 = "345600";
                        updateControlling(idarduino, difftime2, status);
                        alertDialog.dismiss();
                        break;

                    case "2 Hari" :
                        difftime2 = "172800";
                        updateControlling(idarduino, difftime2, status);
                        alertDialog.dismiss();
                        break;

                    case "1 Hari" :
                        difftime2 = "86400";
                        updateControlling(idarduino, difftime2, status);
                        alertDialog.dismiss();
                        break;
                }

            }
        });
    }

    private void deleteControlling(String idarduino) {
        DatabaseReference drControlling = FirebaseDatabase.getInstance().getReference("kendali").child(idarduino);

        drControlling.removeValue();
        Toast.makeText(this, "Controlling is deleted", Toast.LENGTH_LONG).show();
    }

    private boolean updateControlling(String idarduino, String difftime,String manual){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("kendali").child(idarduino);

        databaseReference.child("manual").setValue(manual);
        databaseReference.child("diffTime").setValue(difftime);

        Toast.makeText(this, "Controlling Update Succesfully", Toast.LENGTH_LONG).show();

        return true;

    }
}