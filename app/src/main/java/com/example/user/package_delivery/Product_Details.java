package com.example.user.package_delivery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class Product_Details extends AppCompatActivity {
    CheckBox product1,product2,product3,product4,product5;
    TextView price1,price2,price3,price4,price5;
    Button next;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__details);
        product1=(CheckBox)findViewById(R.id.product1);
        product2=(CheckBox)findViewById(R.id.product2);
        product3=(CheckBox)findViewById(R.id.product3);
        product4=(CheckBox)findViewById(R.id.product4);
        product5=(CheckBox)findViewById(R.id.product5);
        price1=(TextView)findViewById(R.id.price1);
        price2=(TextView)findViewById(R.id.price2);
        price3=(TextView)findViewById(R.id.price3);
        price4=(TextView)findViewById(R.id.price4);
        price5=(TextView)findViewById(R.id.price5);
        next=(Button)findViewById(R.id.button);

       // Toast.makeText(this,"total items"+count,Toast.LENGTH_LONG).show();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(product1.isChecked())
                {
                    count++;
                }
                if(product2.isChecked())
                {
                    count++;
                }
                if(product3.isChecked())
                {
                    count++;
                }
                if(product4.isChecked())
                {
                    count++;
                }
                if(product5.isChecked())
                {
                    count++;
                }
                Intent intent=new Intent(Product_Details.this,Place_Order.class);
                intent.putExtra("total",count);
                startActivity(intent);
            }
        });


    }

}
