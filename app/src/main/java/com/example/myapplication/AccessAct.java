package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AccessAct extends AppCompatActivity {

    private double accountBalance  = 0.0;

    String Email = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_access);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
             Email = "Email : "+String.valueOf(getIntent().getStringExtra("Email_Key"));

        ((TextView)findViewById(R.id.idView)).setText (Email);
        ((TextView)findViewById(R.id.loanBalanceView)).setText (String.valueOf(getIntent().getDoubleExtra("loanBalance_Key",0.0)));
        ((TextView)findViewById(R.id.userNametxt)).setText ("Loaner : "+String.valueOf(getIntent().getStringExtra("names_key")));




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);
        return true;
    }

    public void launchWithdraw(View v){
        Intent i  = new Intent(this,WidthdrawAct.class);
        startActivity(i);
    }

    public void launchApplyLoan(View v){
        Intent i  = new Intent(AccessAct.this,LoanApplyAct.class);
        i.putExtra("Email_key", Email);
        startActivity(i);
    }

    public void showDialog(View v) {

        // Create an EditText for user input

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);// Change this if you want to restrict to numbers
        input.setPadding(10,10,10,10);
        input.requestFocus();


        input.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        }, 200);

        // Create the AlertDialog
        new AlertDialog.Builder(this)
                .setTitle("Enter Information")
                .setMessage("Please enter a number or text:")
                .setView(input) // Set the EditText as the dialog view
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String userInput = input.getText().toString();

                        // Handle the user input (e.g., store it, display it, etc.)
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel(); // Close the dialog
                    }
                })
                .show();
    }




    public void launchRepayLoan(View v){

        Intent i = new Intent(this,RepayLoan.class);
        startActivity(i);

    }
}