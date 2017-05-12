package com.example.aisling.finalprojectaislingstafford;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText firstNameView;
    private EditText lastNameView;
    private RadioGroup genderRadioGroup;

    private String enteredEmail;
    private String enteredPassword;
    private String enteredFirstName;
    private String enteredLastName;

    private String selectedGender;
    private Button nextButton;
    private String imageURl;
    private String emailPattern;

    private String FIRST_NAME;
    private String LAST_NAME;
    private String GENDER;
    private String EMAIL;
    private String PASSWORD;
    private String IMAGE_URL;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        firstNameView = (EditText) findViewById(R.id.first_name);
        lastNameView = (EditText) findViewById(R.id.last_name);
        genderRadioGroup = (RadioGroup) findViewById(R.id.genderGroup);

        nextButton = (Button) findViewById(R.id.register_next_button);

        nextButton.setOnClickListener(this);



    }


    private void getdata()
    {
        enteredEmail = mEmailView.getText().toString();
        enteredPassword = mPasswordView.getText().toString();
        enteredFirstName = firstNameView.getText().toString();
        enteredLastName = lastNameView.getText().toString();

        selectedGender = getGender();

        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (selectedGender.equalsIgnoreCase("Male")) {


            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profilepicturemale);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            byte[] byte_arr = stream.toByteArray();
            imageURl = Base64.encodeToString(byte_arr, Base64.DEFAULT);

        } else if (selectedGender.equalsIgnoreCase("Female")) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profilepicturefemale);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            byte[] byte_arr = stream.toByteArray();
            imageURl = Base64.encodeToString(byte_arr, Base64.DEFAULT);

        }

        if(enteredFirstName.equals(""))
        {
            Toast.makeText(RegisterActivity.this, "First Name must be filled", Toast.LENGTH_LONG).show();
            return;
        }
        else if(enteredLastName.equals(""))
        {
            Toast.makeText(RegisterActivity.this, "Last Name must be filled", Toast.LENGTH_LONG).show();
            return;
        }
        else if (enteredEmail.equals(""))
        {

          Toast.makeText(RegisterActivity.this, "Email must be filled", Toast.LENGTH_LONG).show();
         return;
        }
        else if(enteredPassword.equals(""))
       {
           Toast.makeText(RegisterActivity.this, "Password must be filled", Toast.LENGTH_LONG).show();
           return;
       }
       else if (enteredEmail.length() <= 1 || enteredPassword.length() <= 1) {

            Toast.makeText(RegisterActivity.this, "Email or password length must be greater than one", Toast.LENGTH_LONG).show();

            return;

        }
        else if (!enteredEmail.matches(emailPattern))
        {
            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();

            return;
        }
        else
        {

            Intent intent = new Intent(RegisterActivity.this, RegisterNextActivity.class);


            Bundle bundle = new Bundle();
            bundle.putString("FIRST_NAME", enteredFirstName);
            bundle.putString("LAST_NAME", enteredLastName);
            bundle.putString("GENDER", selectedGender);
            bundle.putString("EMAIL", enteredEmail);
            bundle.putString("PASSWORD", enteredPassword);
            bundle.putString("IMAGE_URL", imageURl);


//Add the bundle to the intent
            intent.putExtras(bundle);

            startActivity(intent);
        }

    }



        @Override
    public void onClick(View v)
    {

        if (v == nextButton)
        {
            getdata();
        }
    }




    private String getGender()
    {

        switch (genderRadioGroup.getCheckedRadioButtonId())
        {
            case R.id.genderMale:
            {
                return "Male";
            }
            case R.id.genderFemale:
            {
                return "Female";
            }
            default:
            {
                return "Male";
            }
        }

    }

}