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

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button login_btn;
    EditText login_email, login_password;
    TextView login_btn_signUp, login_btn_forgotPassword;

    RelativeLayout activity_main;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Login View
        login_btn = (Button) findViewById(R.id.login_btn);
        login_email = (EditText) findViewById(R.id.login_email);
        login_password = (EditText) findViewById(R.id.login_password);
        login_btn_signUp = (TextView) findViewById(R.id.login_btn_signUp);
        login_btn_forgotPassword = (TextView) findViewById(R.id.login_btn_forgotPassword);
        activity_main = (RelativeLayout) findViewById(R.id.activity_main);

        login_btn.setOnClickListener(this);
        login_btn_signUp.setOnClickListener(this);
        login_btn_forgotPassword.setOnClickListener(this);

        //Initialize Firebase
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(Login.this, Dashboard.class));
        }

    }
    
    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.login_btn){

            loginUser(login_email.getText().toString(), login_password.getText().toString());
        }

        if(v.getId() == R.id.login_btn_forgotPassword){
            startActivity(new Intent(Login.this, ForgotPassword.class));
            finish();
        }

        if(v.getId() == R.id.login_btn_signUp){
            startActivity(new Intent(Login.this, SignUp.class));
            finish();
        }

    }

    private void loginUser(String email, final String password){
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (! task.isSuccessful()){

                            if(password.length() < 0){

                                Snackbar snackBar = Snackbar.make(activity_main, "Password Length must be over 6", Snackbar.LENGTH_SHORT);
                                snackBar.show();

                            }else{
                                startActivity(new Intent(Login.this, Dashboard.class));
                            }

                        }

                    }
                });
    }

}
