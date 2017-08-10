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
import com.google.firebase.auth.FirebaseUser;

import butterknife.InjectView;
import butterknife.OnClick;

public class Account extends AppCompatActivity {


    @InjectView(R.id.account_welcome)
    TextView account_welcome;
    @InjectView(R.id.etNewPassword)
    EditText etNewPassword;
    @InjectView(R.id.bt_logout)
    Button bt_logout;
    @InjectView(R.id.bt_changePassword)
    Button bt_changePassword;
    @InjectView(R.id.cv)
    CardView cv;

    RelativeLayout activity_account;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        auth = FirebaseAuth.getInstance();

        //Session Checking
        if(auth.getCurrentUser() != null){
            account_welcome.setText("Welcome, " +auth.getCurrentUser().getEmail());
        }

    }

    @OnClick({R.id.bt_logout, R.id.bt_changePassword})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_logout:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                logoutUser();


                break;
            case R.id.bt_changePassword:
                Explode explode = new Explode();
                explode.setDuration(500);
                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);

                changePassword(etNewPassword.getText().toString());

                break;
        }
    }

    private void logoutUser() {
        auth.signOut();
        if(auth.getCurrentUser() == null){
            startActivity(new Intent(Account.this, MainActivity.class));
            finish();
        }
    }

    private void changePassword(String newPassword) {

        FirebaseUser user = auth.getCurrentUser();
        user.updatePassword(newPassword).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Snackbar snackBar = Snackbar.make(activity_account, "Password Changed Successfully", Snackbar.LENGTH_SHORT);
                    snackBar.show();
                }
            }
        });
    }
}
