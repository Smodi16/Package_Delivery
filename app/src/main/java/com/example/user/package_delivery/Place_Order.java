package com.example.user.package_delivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Place_Order extends AppCompatActivity {
    private FusedLocationProviderClient mFusedLocationClient;
    private DatabaseReference mDatabase;
    private DatabaseReference mPostReference;
    Button place_order,save;
    int total_orders;
    TextView total;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place__order);
        TextView textView=(TextView) findViewById(R.id.landmark);
         editText=(EditText)findViewById(R.id.editText);
        total=(TextView)findViewById(R.id.total);
        save=(Button)findViewById(R.id.save);
        Intent intent=getIntent();
        total_orders=intent.getIntExtra("total",0);
        total.setText("Total Number Of Items: "+total_orders);
        place_order=(Button)findViewById(R.id.button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().length() > 0)
                {
                    // Create object of SharedPreferences.
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Place_Order.this);
                    //now get Editor
                    SharedPreferences.Editor editor = sharedPref.edit();
                    //put your value
                    editor.putString("LandMark",editText.getText().toString());

                    //commits your edits
                    editor.commit();
                }

            }
        });

        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Your Order Has Been Placed Successfully",Toast.LENGTH_LONG).show();
                mDatabase = FirebaseDatabase.getInstance().getReference();

                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Place_Order.this);
                if (ContextCompat.checkSelfPermission(Place_Order.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(Place_Order.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }
                mFusedLocationClient.getLastLocation().addOnSuccessListener(Place_Order.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        System.out.println("location fetched success");
                        String id= mDatabase.push().getKey();
                        System.out.println("id : "+id);
                        System.out.println(""+location.getLatitude()+location.getLongitude());
                        mDatabase.child("temp").setValue("temp");
                        Push_Location push_location=new Push_Location(id,location.getLatitude(),location.getLongitude());
                        mDatabase.child("loc")
                                .child(id)
                                .setValue(push_location)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        System.out.println("location successfully written");
                                       // Place_Order.this.finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                            }
                        });
                        //  Toast.makeText(this,"Value added",Toast.LENGTH_LONG).show();
                        System.out.println(location);
                        if (location != null) {
                            // Logic to handle location object
                        }
                    }
                });

            }
        });






    }

}
