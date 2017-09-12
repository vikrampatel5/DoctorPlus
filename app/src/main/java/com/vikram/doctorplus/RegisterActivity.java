package com.vikram.doctorplus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference mRef;

    Button regButton;
    EditText uname;
    EditText pass;
    EditText email;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        email = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.pass);
        uname = (EditText)findViewById(R.id.uname);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference();
        progressDialog = new ProgressDialog(this);

        if (user != null) {
            startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
            finish();
        }

        regButton = (Button)findViewById(R.id.button_reg);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        StringBuilder errorMessage = new StringBuilder("Please Enter : ");
        final String userEmail = email.getText().toString();
        final String userPass =pass.getText().toString();
        final String userUname = uname.getText().toString();

        if(userUname.contains(" ")){
            uname.setError("Spaces are not allowed!!");
            Toast.makeText(RegisterActivity.this, "No Spaces Allowed", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(userEmail)||TextUtils.isEmpty(userPass)||TextUtils.isEmpty(userUname)){
            if(TextUtils.isEmpty(userUname)){
                errorMessage.append("Username, ");
            }
            if(TextUtils.isEmpty(userEmail)){
                errorMessage.append("Email, ");
            }
            if(TextUtils.isEmpty(userPass)){
                errorMessage.append("Password ");
            }
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }

        progressDialog.setMessage("Registering Please wait... ");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(userEmail,userPass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    DatabaseReference userRef = mRef.child("Users");
                    User users = new User(userUname,userEmail,userPass);
                    userRef.child(userUname).setValue(users);
                    //finish();
                    //start another activity


                    Toast.makeText(RegisterActivity.this, "Registration Successfull!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Registration Error!!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
                startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                finish();
            }
        });
    }
}
