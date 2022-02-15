package com.example.focusedhubapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.NoSuchAlgorithmException;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{
    private TextView register,forgotPassword;
    private EditText setEmail,setPassword;
    private Button signIn;
    private ImageView image;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        register = findViewById(R.id.Register);
        register.setOnClickListener(this);

        signIn = findViewById(R.id.ButtonLogin);
        signIn.setOnClickListener(this);

        setEmail = findViewById(R.id.Username);
        setPassword = findViewById(R.id.Password);

        mAuth = FirebaseAuth.getInstance();
        forgotPassword = findViewById(R.id.Forgot);
        forgotPassword.setOnClickListener(this);

        image = findViewById(R.id.imageView2);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this,MainActivity.class));

            }
        });

    }

//on click take to register user class
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Register:
            startActivity(new Intent(this,RegisterUser.class));
            break;

            case R.id.ButtonLogin:
                try {
                    userLogin();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.Forgot:
                startActivity(new Intent(this,ForgotPassword.class));
                break;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void userLogin() throws NoSuchAlgorithmException {
        String email = setEmail.getText().toString().trim();
        String password = setPassword.getText().toString().trim();
        Security security = new Security();
        password = security.ConvertToHash(password);

        if(setEmail.getText().toString().isEmpty()){
            setEmail.setError("Email is required");
            setEmail.requestFocus();
            return;
        }

        if(setPassword.getText().toString().isEmpty()){
            setPassword.setError("Please enter a password");
            setPassword.requestFocus();
            return;
        }
        if(setPassword.getText().toString().length()<6){
            setPassword.setError("Password must be atleast 6 characters");
            setPassword.requestFocus();

        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                if(user1.isEmailVerified()){
                    startActivity(new Intent(MainActivity2.this,MainActivity.class));

                }else{
                    user1.sendEmailVerification();
                    Toast.makeText(MainActivity2.this,"Check your email to verify your account",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(MainActivity2.this,"Failed to login please check credentials",Toast.LENGTH_LONG).show();
            }
        });

    }
}