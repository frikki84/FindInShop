package com.dziadkouskaya.findinshop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;


import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
                .setTosAndPrivacyPolicyUrls(
                "https://www.dropbox.com/s/neye2sfjyeyh2ca/%D0%9F%D0%BE%D0%BB%D0%B8%D1%82%D0%B8%D0%BA%D0%B0%20%D0%BA%D0%BE%D0%BD%D1%84%D0%B8%D0%B4%D0%B5%D0%BD%D1%86%D0%B8%D0%B0%D0%BB%D1%8C%D0%BD%D0%BE%D1%81%D1%82%D0%B8.docx?dl=0",
                    "https://www.dropbox.com/s/neye2sfjyeyh2ca/%D0%9F%D0%BE%D0%BB%D0%B8%D1%82%D0%B8%D0%BA%D0%B0%20%D0%BA%D0%BE%D0%BD%D1%84%D0%B8%D0%B4%D0%B5%D0%BD%D1%86%D0%B8%D0%B0%D0%BB%D1%8C%D0%BD%D0%BE%D1%81%D1%82%D0%B8.docx?dl=0")
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
