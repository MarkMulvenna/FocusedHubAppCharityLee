package com.example.focusedhubapp;

import static java.lang.System.exit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class PinControl extends AppCompatActivity {
    int numTries = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_control);


        TextView txtPinControl = findViewById(R.id.txtPinCode);

        Button btnSubmit = findViewById(R.id.btnSubmitPin);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pinControl = Integer.parseInt(txtPinControl.getText().toString());
                    if (pinControl == 2019)
                    {
                       AlertDialog.Builder builder = new AlertDialog.Builder(PinControl.this);
                       builder.setPositiveButton("Switch Accounts", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }

                       });
                       builder.setNegativeButton("Close app", new DialogInterface.OnClickListener() {

                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               System.exit(0);
                           }
                       });

                       builder.show();

                    }
                    else if (numTries != 0){
                        Toast.makeText(PinControl.this, "Sign in failed, you have " + numTries + " attempts remaining", Toast.LENGTH_SHORT).show();
                        numTries--;
                    }
                    else {
                        startActivity(new Intent(PinControl.this, MainActivity.class));
                    }
                }

        });

    }
}