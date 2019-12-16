package com.dziadkouskaya.findinshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class UserManual extends AppCompatActivity {

    Button btnManual;

    ImageView photo_one;
    ImageView photo_two;
    ImageView photo_three;
    ImageView photo_seven;
    ImageView photo_eight;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_info);

        photo_one = findViewById(R.id.manualImage1);
        photo_one.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.manual_one));

        photo_two = findViewById(R.id.manualImage2);
        photo_two.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.manual_two));

        photo_three = findViewById(R.id.manualImage3);
        photo_three.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.manual_three));

        photo_seven = findViewById(R.id.manualImage7);
        photo_seven.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.manual_seven));

        photo_eight = findViewById(R.id.manualImage8);
        photo_eight.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.manual_eight));




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
