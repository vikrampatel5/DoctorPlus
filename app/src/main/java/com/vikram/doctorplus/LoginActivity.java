package com.vikram.doctorplus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout emailTIL;
    TextInputLayout passTIL;
    TextView textRegister;
    Button loginButton;
    FirebaseAuth auth;
    FirebaseUser user;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailTIL = (TextInputLayout)findViewById(R.id.textInputLayout);
        passTIL = (TextInputLayout)findViewById(R.id.textInputLayout2);
        loginButton = (Button)findViewById(R.id.button_login);
        textRegister = (TextView)findViewById(R.id.textRegister);
        progressDialog = new ProgressDialog(this);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if(user!=null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });
    }

    private void login() {
        String email = emailTIL.getEditText().getText().toString();
        String pass = passTIL.getEditText().getText().toString();
        progressDialog.setMessage("Signing in, Please wait!!");
        progressDialog.show();
        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    progressDialog.dismiss();
                }

            }
        });
    }
}
