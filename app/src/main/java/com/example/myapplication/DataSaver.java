package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataSaver extends SQLiteOpenHelper {

    public static final String dbName = "LoanAppDatabase.db";

    public static final int dbVersion  = 2;


    String createTableQuery = "CREATE TABLE LOANEE_DETAILS (firstName Text," +
            "lastName Text,Email Text PRIMARY KEY," +
            "DateOfBirth date,PhoneNumber Text," +
            "Id_Number INTEGER,PassWord Text," +
            "Age Integer,Loan_Limit decimal(10,2),accBalance decimal(10,2))";

    String createLoanTable = "CREATE TABLE LOANS_TABLE(Loan_Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Loanee_Email TEXT,Loan_Amount decimal(10,2)," +
            "dateBorrowed date,interest decimal(2,3),FOREIGN KEY (Loanee_Email) REFERENCES LOANEE_DETAILS(Email) ON DELETE CASCADE)";



    public DataSaver(Context context) {
        super(context, dbName, null, dbVersion);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(createTableQuery);
        sqLiteDatabase.execSQL(createLoanTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("Drop Table if exists Loanee_details");
        sqLiteDatabase.execSQL("Drop Table if exists Loans_Table");

        onCreate(sqLiteDatabase);


    }

    public boolean addLoaneee (String firstName,String lastName,String Email,String DoB,String phoneNumber,int idNumber,String password,int Age){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values  = new ContentValues();

        values.put("firstName",firstName);
        values.put("lastName",lastName);
        values.put("Email",Email);
        values.put("DateOfBirth",DoB);
        values.put("PhoneNumber",phoneNumber);
        values.put("Id_Number",idNumber);
        values.put("PassWord",password);
        values.put("Age",Age);
        values.put("Loan_Limit",20000);
        long result = db.insert("Loanee_Details",null,values);
        if(result==-1){

            return false;
        }else {
            return true;
        }



    }
    public boolean loginUser(String Email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "LOANEE_DETAILS",
                new String[]{"Email","PassWord"},  // Only need username to confirm login
                "Email = ? AND PassWord = ?",  // WHERE clause
                new String[]{Email, password}, // WHERE arguments
                null, null, null);

        boolean loginSuccess = cursor.getCount() > 0; // If cursor has entries, login is successful

        cursor.close();
        db.close();
        return loginSuccess;
    }

    public boolean addLoan (String loaneeEmail,double loanAmount,String dateBorrowed,double interest) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put("Loanee_Email", loaneeEmail);
        values.put("Loan_Amount", loanAmount);
        values.put("dateBorrowed", dateBorrowed);
        values.put("interest", interest);
        long result = db.insert("Loans_Table", null, values);
        if (result == -1) {

            return false;
        } else {
            return true;
        }
    }

    public String getSpecificField() {
        String Email = "";
        String firstName = "";

        SQLiteDatabase sbd = this.getReadableDatabase();

        String[] columns = {"Email"};

        Cursor cursor = sbd.query(
                "LOANEE_DETAILS",

                columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {

                Email = cursor.getString(cursor.getColumnIndexOrThrow("Email"));


            } while (cursor.moveToNext());
        }
        cursor.close();
        sbd.close();





        return Email;

    }

    @SuppressLint("Range")
    public double getLoanAmountByEmail(String email) {

        double loanAmount = 0.0;
        SQLiteDatabase db = this.getReadableDatabase();


        String query = "SELECT Loan_Amount " +
                "FROM LOANS_TABLE " +
                "INNER JOIN LOANEE_DETAILS " +
                "ON LOANS_TABLE.Loanee_Email = LOANEE_DETAILS.Email " +
                "WHERE LOANEE_DETAILS.Email = ?";

        Cursor cursor = db.rawQuery(query, new String[]{email});
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                loanAmount = cursor.getDouble(cursor.getColumnIndexOrThrow("Loan_Amount"));
            }
            cursor.close();
        }

        return loanAmount;
    }

    public String fetchNamesbyEmail(String email){
        String firstName = "";
        String lastName = "";
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT firstName,lastName FROM Loanee_Details " +
                         "WHERE Email = ?";

        Cursor cursor = db.rawQuery(query, new String[]{email});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                firstName = cursor.getString(cursor.getColumnIndexOrThrow("firstName"));
                lastName = cursor.getString(cursor.getColumnIndexOrThrow("lastName"));
            }
            cursor.close();
        }



        return (firstName+" "+lastName);

    }

}











