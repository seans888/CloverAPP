package com.cloverapp.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText login_email,login_password;
    Button login_btn;
    TextView link_signup;

    FirebaseAuth auth;

    RelativeLayout activity_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_email = (EditText) findViewById(R.id.login_email);
        login_password = (EditText) findViewById(R.id.login_pwrd);
        login_btn = (Button) findViewById(R.id.login_btn);
        link_signup = (TextView) findViewById(R.id.link_signup);

        login_btn.setOnClickListener(this);
        link_signup.setOnClickListener(this);


        //Initialize Firebase
        auth = FirebaseAuth.getInstance();

        //Session Checker
        if(auth.getCurrentUser() != null){
            startActivity(new Intent(Login.this, Account.class));
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.link_signup) {
            startActivity(new Intent(Login.this, Signup.class));
            finish();
        }else if (v.getId() == R.id.login_btn){
            loginUser(login_email.getText().toString(), login_password.getText().toString());
            finish();
        }
    }

    private void loginUser(String email, final String password) {
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            if(password.length() < 0){
                                Snackbar snackBar = Snackbar.make(activity_login, "Password Length must be over 6", Snackbar.LENGTH_SHORT);
                                snackBar.show();
                            }else{
                                startActivity(new Intent(Login.this, Account.class));
                                finish();
                            }
                        }
                    }
                });
    }
}
