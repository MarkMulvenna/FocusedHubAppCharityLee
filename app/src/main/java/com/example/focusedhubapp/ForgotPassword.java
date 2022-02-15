package com.example.focusedhubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;

public class ForgotPassword extends AppCompatActivity {
    private EditText emailEdit;
    private Button resetPass;
    private ImageView banner;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailEdit = (EditText) findViewById(R.id.Email1);
        resetPass = (Button) findViewById(R.id.button12);
        auth = FirebaseAuth.getInstance();
        banner = findViewById(R.id.imageView1);
        banner.setOnClickListener(v -> {
            startActivity(new Intent(ForgotPassword.this, MainActivity.class));
        });


        resetPass.setOnClickListener(v -> resetPass());

    }

    private void resetPass() {
        String email = emailEdit.getText().toString().trim();
        if (email.isEmpty()) {
            emailEdit.setError("Email is required");
            emailEdit.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEdit.setError("Please enter valid email!");
            emailEdit.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ForgotPassword.this, "Check your email to reset your password", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ForgotPassword.this, "Something wrong happened", Toast.LENGTH_LONG).show();
            }
        });

    }
}
