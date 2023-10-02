package com.example.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;



public class ShopActivity extends AppCompatActivity {
ImageButton paginaPc, paginaPhone, paginaAc;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        paginaPc = (ImageButton) findViewById(R.id.pc_page) ;
        paginaPhone= (ImageButton) findViewById(R.id.phone_page);
        paginaAc = (ImageButton) findViewById(R.id.ac_page);

        paginaPc.setOnClickListener(v -> {
            Intent intent = new Intent(ShopActivity.this, Pc_Activity.class);
            startActivity(intent);
        });

        paginaPhone.setOnClickListener(v -> {
            Intent intentP = new Intent(ShopActivity.this, Iphone_Activity.class);
            startActivity(intentP);
        });

        paginaAc.setOnClickListener(v -> {
            Intent intentAc = new Intent(ShopActivity.this, Ac_Activity.class);
            startActivity(intentAc);
        });

    }

}