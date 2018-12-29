package com.example.user.package_delivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Show_Order extends AppCompatActivity {
    private DatabaseReference mPostReference;
    ArrayList<Double> lat_arrayList;
    ArrayList<Double> lon_arraylist;
    //static double source_lat;
    double source_lat;
    double source_long;
    //static double source_long;
    public double lat;
    public double lon;
    TextView textView,textView2;
    String srcAdd;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Button navigate;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__order);
        textView=(TextView)findViewById(R.id.textView);
        textView2=(TextView)findViewById(R.id.landmark);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String landmark = sharedPref.getString("LandMark", "Not Applicable");
        textView2.setText("LandMark: "+landmark);
        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = database.getReference().child("loc");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        System.out.println("data fetched");
                        int i = 0;
                        lat_arrayList = new ArrayList<>();
                        lon_arraylist=new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            System.out.println("data : " + snapshot.getValue());
                            User user = snapshot.getValue(User.class);
                            lat = user.getLatitude();
                          //  arrayList.add(lat);
                   //         Toast.makeText(Show_Order.this, "latitude"+lat, Toast.LENGTH_SHORT).show();
//                            lat_arr[i]=lat;
                   //         System.out.println("latitude: " +lat);
                          lon = user.getLongitude();
//                           lon_arr[i]=lon;
//                           i++;

                            lat_arrayList.add(snapshot.getValue(User.class).getLatitude());
                            lon_arraylist.add(snapshot.getValue(User.class).getLongitude());
                            System.out.println("array " + lat_arrayList);
                          //  Toast.makeText(Show_Order.this,"list of array ; " +lat_arr,Toast.LENGTH_LONG).show();

                        }
                        //  System.out.println("latitude "+lat_arr);
                      // loc(lat, lon);
                        try_map(lat_arrayList,lon_arraylist);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        System.out.println("databaseError : " + databaseError.getMessage());
                    }

                });
            }
            public void loc(double lat,double lon){
                com.example.user.package_delivery.Location location=new com.example.user.package_delivery.Location();
                //System.out.println("latitude "+lat_arr);
                location.setLat(lat);
                location.setLng(lon);
                //location.setLng(Double.parseDouble());
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+location.getLat()+","+location.getLng());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
            public void try_map(ArrayList<Double> lat_arraylist, ArrayList<Double> lon_arraylist)
            {

                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Show_Order.this);
                if (ContextCompat.checkSelfPermission(Show_Order.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(Show_Order.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }
                mFusedLocationClient.getLastLocation().addOnSuccessListener(Show_Order.this, new OnSuccessListener<android.location.Location>() {
                    @Override
                    public void onSuccess(android.location.Location location) {
                        // Got last known location. In some rare situations this can be null.
                        System.out.println("location fetched success");
                        source_lat=(location.getLatitude());
                       source_long=(location.getLongitude());
                       System.out.println("Source Latitude: "+source_lat);
                         srcAdd = "saddr="+Double.toString(source_lat)+","+Double.toString(source_long);
                        //  Toast.makeText(this,"Value added",Toast.LENGTH_LONG).show();
                        System.out.println(location);
                        if (location != null) {
                            // Logic to handle location object
                        }
                    }
                });
                System.out.println("Source :"+source_lat);
                //String srcAdd = "saddr="+Double.toString(source_lat)+","+Double.toString(source_long);
                String desAdd = "&daddr="+lat_arraylist.get(lat_arraylist.size() - 1)+","+lon_arraylist.get(lon_arraylist.size() - 1);
                String wayPoints = "";

                for (int j = 0; j < lat_arraylist.size() - 1; ++j) {

                    wayPoints =wayPoints+"+to:"+lat_arraylist.get(j)+","+lon_arraylist.get(j);
                }

                String link="https://maps.google.com/maps?"+srcAdd+desAdd+wayPoints;
                final Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(link));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }

        });

    }

}