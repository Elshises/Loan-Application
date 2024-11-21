package com.example.myapplication;// RegistrationAct.java

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.time.LocalDate;
import java.util.Calendar;

public class RegistrationAct extends AppCompatActivity {



    private EditText etDateOfBirth;

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etDateOfBirth = findViewById(R.id.etDateOfBirth);

        etDateOfBirth.setOnClickListener(v -> showDatePickerDialog());


        new DataSaver(this);


    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

         datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    etDateOfBirth.setText(date);
                }, year, month, day);

        datePickerDialog.show();



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
    }

    public void getFormData(View view) {

        String finalPassword = "";

        String FirstName = ((EditText) findViewById(R.id.etFirstName)).getText().toString();

        String LastName = ((EditText) findViewById(R.id.etLastName)).getText().toString();

        String Email = ((EditText) findViewById(R.id.etEmail)).getText().toString();

        String Id = ((EditText) findViewById(R.id.etIdNumber)).getText().toString();
        int IdNumber = Integer.parseInt(Id);

        String phoneNumber = ((EditText) findViewById(R.id.etPhoneNumber)).getText().toString();

        String dateOfBirth = ((EditText) findViewById(R.id.etDateOfBirth)).getText().toString();

        int birthYear = datePickerDialog.getDatePicker().getYear();

        int thisYear = 1000;

        LocalDate date = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = LocalDate.now();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            thisYear = date.getYear();
        }

        int Age = thisYear - birthYear;


        String passWord = ((EditText) findViewById(R.id.etPassword)).getText().toString();

        String confirmPassword = ((EditText) findViewById(R.id.etConfirmPassword)).getText().toString();

        if (passWord.equals(confirmPassword)) {

            finalPassword = passWord;

            if(new DataSaver(this).addLoaneee(FirstName,LastName,Email,dateOfBirth,phoneNumber,IdNumber,finalPassword,Age))
                Toast.makeText(this, "Registration Successfull!", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this,LoginAct.class));

        }
        else {

            ((TextView) findViewById(R.id.errorView)).setText("Passwords don't match  please Try again");
        }


        }


    }







