package com.cloverapp.Auth;

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

import com.cloverapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    EditText signup_email, signup_password;
    TextView link_login;
    Button register_btn;

    FirebaseAuth auth;

    RelativeLayout activity_signup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup_email = (EditText) findViewById(R.id.signup_email);
        signup_password = (EditText) findViewById(R.id.signup_pwrd);
        register_btn = (Button) findViewById(R.id.register_btn);
        link_login = (TextView) findViewById(R.id.link_login);

        register_btn.setOnClickListener(this);
        link_login.setOnClickListener(this);

        //Initialize Firebase
        auth = FirebaseAuth.getInstance();



    }

    @Override
    public void onClick(View v) {


        if(v.getId() == R.id.link_login){
            startActivity(new Intent(Signup.this, Login.class));
        }else if(v.getId() == R.id.register_btn){
            signUpUser(signup_email.getText().toString(), signup_password.getText().toString());
            startActivity(new Intent(Signup.this, Login.class));
            finish();
        }


    }

    private void signUpUser(final String email, String password) {

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
