package com.cloverapp.android.UserAuth;

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

import com.cloverapp.android.Inventory.Inventory;
import com.cloverapp.android.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {

    EditText input_email, input_password;
    TextView link_signup;
    Button btn_login;

    RelativeLayout activity_login;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        link_signup = (TextView) findViewById(R.id.link_signup);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(this);
        link_signup.setOnClickListener(this);

        //Initialize Firebase
        auth = FirebaseAuth.getInstance();

        //Session Checker
        if(auth.getCurrentUser() != null){
            startActivity(new Intent(LoginPage.this, Inventory.class));
            finish();
        }

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.link_signup) {
            startActivity(new Intent(LoginPage.this, SignUp.class));
            finish();
        }else if (v.getId() == R.id.btn_login){
            loginUser(input_email.getText().toString(), input_password.getText().toString());
        }
    }

    private void loginUser(String email, final String password) {
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()){

                            if(password.length() < 0){

                                Snackbar snackBar = Snackbar.make(activity_login, "Password Length must be over 6", Snackbar.LENGTH_SHORT);
                                snackBar.show();

                            }
                            else{
                                startActivity(new Intent(LoginPage.this, Inventory.class));
                            }

                        }

                    }
                });
    }
}
