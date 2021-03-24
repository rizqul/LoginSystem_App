package com.example.learnapp4_loginsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassActivity extends AppCompatActivity {

    EditText emailid;
    Button reset;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        emailid = findViewById(R.id.emailField);
        mFirebaseAuth = FirebaseAuth.getInstance();
        reset = findViewById(R.id.resetPassBtn);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailid.getText().toString();

                if (email.isEmpty()){
                    Toast.makeText(ResetPassActivity.this, "Tolong masukkan email", Toast.LENGTH_SHORT).show();
                } else {
                    mFirebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ResetPassActivity.this, "Reset password terkirim ke email anda", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ResetPassActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(ResetPassActivity.this, "Error mengirim reset password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}