package com.example.focusedhubapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.security.NoSuchAlgorithmException;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private EditText txtCharity, txtEmail, txtPassword;
    private Button btnRegisterUser;
    private ImageView imgBanner;
    String charity, email, password;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        imgBanner = (ImageView) findViewById(R.id.banner);
        imgBanner.setOnClickListener(this);

        btnRegisterUser = (Button) findViewById(R.id.buttonReg);
        btnRegisterUser.setOnClickListener(this);

        txtCharity = findViewById(R.id.charity);
        txtEmail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.passwordReg);

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, MainActivity2.class));
                break;
            case R.id.buttonReg:
                try {
                    if (validateDetails())
                    {
                        registerUser();
                    }
                    else
                    {
                        Toast.makeText(RegisterUser.this, "Action Failed.", Toast.LENGTH_SHORT).show();
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
        }
    }

    private void registerUser() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            User1 user = new User1(charity, email, password, 1111);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(RegisterUser.this, "Signup Successful! Please check your email for verification.", Toast.LENGTH_SHORT).show();
                                    FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
                                    startActivity(new Intent(RegisterUser.this, MainActivity2.class));
                                }
                            });
                        }
                        else
                        {
                            if (!task.isSuccessful())
                            {
                                try {
                                    throw task.getException();
                                }
                                catch (FirebaseAuthUserCollisionException firebaseAuthUserCollisionException)
                                {
                                    firebaseAuthUserCollisionException.printStackTrace();
                                    Toast.makeText(RegisterUser.this, "Email already in use.", Toast.LENGTH_SHORT).show();
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }

                            Toast.makeText(RegisterUser.this, "Signup unsuccessful! Please check your details and try again.", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean validateDetails() throws NoSuchAlgorithmException {
        boolean charityNotNull = false;
        boolean emailNotNull = false;
        boolean passwordNotNull = false;

        if (txtCharity.getText().toString().isEmpty()) {
            txtCharity.setError("Please enter charity name");
            txtCharity.requestFocus();

        } else {
            charityNotNull = true;
        }
        if (txtPassword.getText().toString().isEmpty()) {
            txtPassword.setError("Please enter password");
            txtPassword.requestFocus();
        } else {
            passwordNotNull = true;
        }
        if (txtEmail.getText().toString().isEmpty()) {
            txtEmail.setError("Please enter Email");
            txtEmail.requestFocus();

        } else {
            emailNotNull = true;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText()).matches()) {
            txtEmail.setError("Please provide a valid email");
            txtEmail.requestFocus();

        }
        if (txtPassword.getText().length() < 6) {
            txtPassword.setError("Password should be at least 6 characters");
            txtPassword.requestFocus();
        }

        if (charityNotNull && passwordNotNull && emailNotNull) {
            Security security = new Security();
            charity = txtCharity.getText().toString().trim();
            password = txtPassword.getText().toString().trim();
            password = security.ConvertToHash(password);
            email = txtEmail.getText().toString().trim();
            return true;
        }
        else
        {
            Toast.makeText(RegisterUser.this, "Account could not be registered, unknown error, please check your details re email and password", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}