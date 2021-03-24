package com.example.learnapp4_loginsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText emailid,pass;
    Button signin;
    CheckBox showPass;
    TextView tvdaftar;
    TextView tvreset;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailid = findViewById(R.id.emailField);
        pass = findViewById(R.id.passwordField);
        signin = findViewById(R.id.LogIn);
        tvdaftar = findViewById(R.id.daftar);
        tvreset = findViewById(R.id.forgetPass);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null){
                    Toast.makeText(LoginActivity.this, "Anda Telah Masuk", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Silahkan login terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        };

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailid.getText().toString();
                String password = pass.getText().toString();
                if (email.isEmpty()){
                    emailid.setText("Masukkan Email");
                    emailid.requestFocus();
                }
                else if (password.isEmpty()){
                    pass.setText("Masukkan Password");
                    pass.requestFocus();
                }
                else if (email.isEmpty() && password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Isi Yang Kosong", Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && password.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(i);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(LoginActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvdaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dftr = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(dftr);
            }
        });

        tvreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resetPass = new Intent(LoginActivity.this, ResetPassActivity.class);
                startActivity(resetPass);
            }
        });
    }

    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    public void showPass_box(View view) {


        pass = findViewById(R.id.passwordField);
        showPass = findViewById(R.id.checkbox_showPass);


        if (showPass.isChecked()){
            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}