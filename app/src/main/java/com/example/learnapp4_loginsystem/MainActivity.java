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

public class MainActivity extends AppCompatActivity {

    EditText emailid,pass;
    Button daftar;
    CheckBox showPass;
    TextView masuk;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailid = findViewById(R.id.emailField);
        pass = findViewById(R.id.passwordField);
        daftar = findViewById(R.id.signUpBtn);
        masuk = findViewById(R.id.logInText);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailid.getText().toString();
                String password = pass.getText().toString();
                if (email.isEmpty()){
                    emailid.setText("Masukkan Email");
                    emailid.requestFocus();
                }
                else if(password.isEmpty()){
                    pass.setText("Masukkan Password");
                    pass.requestFocus();
                }
                else if(email.isEmpty() && password.isEmpty()){
                    Toast.makeText(MainActivity.this, "isi yang kosong",Toast.LENGTH_SHORT);
                }
                else if (!(email.isEmpty() && password.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Pendaftaran gagal coba lagi", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }
        });
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(a);
            }
        });
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