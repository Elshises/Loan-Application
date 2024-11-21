package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        new DataSaver(this);
    }



    public void login(View V){


        String Email = ((EditText)findViewById(R.id.etIdNumber)).getText().toString();
        String Password = ((EditText)findViewById(R.id.etPassword)).getText().toString();

        if(new DataSaver(this).loginUser(Email,Password)){
            Toast.makeText(this,"Log In Successfull",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginAct.this,AccessAct.class);

            intent.putExtra("Email_Key",Email);

            double loanBalance = new DataSaver(this).getLoanAmountByEmail(Email);
            intent.putExtra("loanBalance_Key",loanBalance);
            Toast.makeText(this, String.valueOf(loanBalance), Toast.LENGTH_SHORT).show();

            String userName  = new DataSaver(this).fetchNamesbyEmail(Email);
            intent.putExtra("names_key",userName);

            startActivity(intent);








        }else{

            ((TextView)findViewById(R.id.errorView)).setText("Incorrect Email or Password");
        }





    }


}