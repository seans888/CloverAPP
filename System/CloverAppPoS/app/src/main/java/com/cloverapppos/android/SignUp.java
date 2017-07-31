package com.cloverapppos.android;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    Button signup_btn;
    EditText signup_email, signup_password;
    TextView signup_btn_login, signup_btn_forgotPassword;

    RelativeLayout activity_signup;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signup_btn = (Button) findViewById(R.id.signup_btn);
        signup_email = (EditText) findViewById(R.id.signup_email);
        signup_password = (EditText) findViewById(R.id.signup_password);
        signup_btn_login = (TextView) findViewById(R.id.signup_btn_login);
        signup_btn_forgotPassword = (TextView) findViewById(R.id.signup_btn_forgotPassword);
        activity_signup = (RelativeLayout) findViewById(R.id.activity_signup);

        signup_btn.setOnClickListener(this);
        signup_btn_login.setOnClickListener(this);
        signup_btn_forgotPassword.setOnClickListener(this);

        //Initialize Firebase
        auth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.signup_btn){

            signupUser(signup_email.getText().toString(), signup_password.getText().toString());
        }

        if(v.getId() == R.id.login_btn_forgotPassword){
            startActivity(new Intent(SignUp.this, ForgotPassword.class));
            finish();
        }

        if(v.getId() == R.id.signup_btn_login){
            startActivity(new Intent(SignUp.this, SignUp.class));
            finish();
        }

    }

    private void signupUser(final String email, String password) {

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                           Snackbar snackBar = Snackbar.make(activity_signup, "Error: " +task.getException(), Snackbar.LENGTH_SHORT);
                            snackBar.show();
                        }else{
                           Snackbar snackBar = Snackbar.make(activity_signup, "Registration Successful: " + email, Snackbar.LENGTH_SHORT);
                            snackBar.show();
                        }
                    }
                });

    }
}
