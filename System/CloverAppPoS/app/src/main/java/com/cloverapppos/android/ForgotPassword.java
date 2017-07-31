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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    Button forgotpass_btn_reset;
    EditText forgotpass_email;
    TextView forgotpass_back;
    RelativeLayout activity_forgotpass;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotpass_btn_reset = (Button) findViewById(R.id.forgotpass_btn_changepassword);
        forgotpass_email = (EditText) findViewById(R.id.forgotpass_email);
        forgotpass_back = (TextView) findViewById(R.id.forgotpass_btn_back);
        activity_forgotpass = (RelativeLayout) findViewById(R.id.activity_forgotpass);

        forgotpass_btn_reset.setOnClickListener(this);
        forgotpass_back.setOnClickListener(this);

        //Initialize Firebase
        auth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.forgotpass_btn_back){
            startActivity(new Intent(ForgotPassword.this, Login.class));
            finish();
        }else if(v.getId() == R.id.forgotpass_btn_changepassword){
            resetPassword(forgotpass_email.getText().toString());
        }

    }

    private void resetPassword(final String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                     if(task.isSuccessful()){
                        Snackbar snackBar = Snackbar.make(activity_forgotpass, "We have sent the Password Recover Email to: " + email, Snackbar.LENGTH_SHORT);
                        snackBar.show();
                    }else

                    {
                        Snackbar snackBar = Snackbar.make(activity_forgotpass, "Password Reset Email Failed to Send", Snackbar.LENGTH_SHORT);
                        snackBar.show();
                    }
                }
        });

    }
}
