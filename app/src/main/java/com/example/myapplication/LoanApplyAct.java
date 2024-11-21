package com.example.myapplication;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;

public class LoanApplyAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loan_apply);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Set the status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
    }

    public void applyLoan(View v) {
        String message = "You Successfully borrowed ";
        String dateBorrowed = "";
        double loanAmount = Double.parseDouble(((EditText) findViewById(R.id.etApplyAmount)).getText().toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateBorrowed = String.valueOf((LocalDate.now()));
        }
        double loanInterest = loanAmount * 0.15;

        String Email = getIntent().getStringExtra("Email_key");


        TextView txt = (TextView) findViewById(R.id.loanLimittxt);

        if (Double.parseDouble(txt.getText().toString()) < loanAmount) {
            Toast.makeText(this, "Enter a valid Amount", Toast.LENGTH_LONG).show();
        } else {
            if (new DataSaver(this).addLoan(Email, loanAmount, dateBorrowed, loanInterest))
               showMessageDialog(message+String.valueOf(loanAmount)+"Check your Account Balance to widthdraw");
            else
                Toast.makeText(this, " An error occured", Toast.LENGTH_LONG).show();
        }


    }
    private void showMessageDialog(String message) {
        // Create the AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message) // Set the message
                .setCancelable(false) // Prevents dismissal by tapping outside
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle the click event for "OK" button
                        dialog.dismiss(); // Dismiss the dialog
                    }
                });

        // Create and show the dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    }
