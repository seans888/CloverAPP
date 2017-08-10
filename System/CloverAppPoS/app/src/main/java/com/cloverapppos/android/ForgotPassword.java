package com.cloverapppos.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

//    Button forgotpass_btn_reset;
//    EditText forgotpass_email;
//    TextView forgotpass_back;


//    @InjectView(R.id.etRecoveryEmail)
    EditText etRecoveryEmail;
//    @InjectView(R.id.bt_logout)
    Button bt_logout;
//    @InjectView(R.id.bt_recoverPassword)
    Button bt_recoverPassword;
//    @InjectView(R.id.tv_back)
    TextView tv_back;
//    @InjectView(R.id.cv)
    CardView cv;


    RelativeLayout activity_forgotpass;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        bt_recoverPassword = (Button) findViewById(R.id.bt_recoverPassword);
        etRecoveryEmail = (EditText) findViewById(R.id.etRecoveryEmail);
        tv_back = (TextView) findViewById(R.id.tv_back);
        activity_forgotpass = (RelativeLayout) findViewById(R.id.activity_forgotpass);

        bt_recoverPassword.setOnClickListener(this);
        tv_back.setOnClickListener(this);

        //Initialize Firebase
        auth = FirebaseAuth.getInstance();

    }

//    @OnClick({R.id.bt_logout, R.id.bt_changePassword})
    public void onClick(View view) {
        if (view.getId() == R.id.tv_back) {

            getWindow().setExitTransition(null);
            getWindow().setEnterTransition(null);

            startActivity(new Intent(ForgotPassword.this, MainActivity.class));
            finish();

        }else if (view.getId() == R.id.bt_recoverPassword) {
            Explode explode = new Explode();
            explode.setDuration(500);
            getWindow().setExitTransition(explode);
            getWindow().setEnterTransition(explode);

            resetPassword(etRecoveryEmail.getText().toString());
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
