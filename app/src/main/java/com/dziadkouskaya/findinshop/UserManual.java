package com.dziadkouskaya.findinshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserManual extends AppCompatActivity {

    Button btnManual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_info);

        btnManual = findViewById(R.id.btnFromManual);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserManual.this, MainActivity.class);
                startActivity(intent);

            }
        };

        btnManual.setOnClickListener(onClickListener);


    }
}
