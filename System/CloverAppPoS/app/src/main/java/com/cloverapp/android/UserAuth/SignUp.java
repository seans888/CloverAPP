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

import com.cloverapp.android.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    EditText input_email, input_password;
    TextView link_forgotPassword;
    Button btn_register;

    FirebaseAuth auth;

    RelativeLayout activity_signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        btn_register = (Button) findViewById(R.id.btn_register);
        link_forgotPassword = (TextView)findViewById(R.id.link_forgotPassword);

        btn_register.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.link_forgotPassword){
            startActivity(new Intent(SignUp.this, ForgotPassword.class));
        }else if(v.getId() == R.id.btn_register){
            signUpUser(input_email.getText().toString(), input_password.getText().toString());
            startActivity(new Intent(SignUp.this, LoginPage.class));
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
