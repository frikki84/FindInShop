package com.example.findinshop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;


public class RegistrationForm extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 2408; //any number
    private FirebaseAuth mAuth;
    List<AuthUI.IdpConfig> providers;
    Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_form);

        btnBack = findViewById(R.id.buttonBack);


        AuthUI.getInstance()
                .signOut(RegistrationForm.this);

        Intent intent =new Intent(RegistrationForm.this, MainActivity.class);
        startActivity(intent);
/*
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logout
                AuthUI.getInstance()
                        .signOut(RegistrationForm.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                buttonLogin.setEnabled(false);
                                showSignInOptions();
                            }
                        } ).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegistrationForm.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
                Intent intent =new Intent(RegistrationForm.this, MainActivity.class);
                startActivity(intent);

            }
        });
*/
        //init providers
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(), //email builder
                new AuthUI.IdpConfig.PhoneBuilder().build(),//phone builder
                new AuthUI.IdpConfig.GoogleBuilder().build() //GOOGLE ACCOUNT
        );
        showSignInOptions();
    }

    private void showSignInOptions() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.myStyle)
                .build(), MY_REQUEST_CODE
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IdpResponse response = IdpResponse.fromResultIntent(data);
        if (requestCode == MY_REQUEST_CODE) {
            //get user
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            //show email
            Toast.makeText(this, "" + user.getEmail(), Toast.LENGTH_SHORT).show();

            //set button singout
            //buttonLogin.setEnabled(true);
        } else {
            Toast.makeText(this, "" + response.getError(), Toast.LENGTH_SHORT).show();
        }
    }
}
