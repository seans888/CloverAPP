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
import com.google.firebase.auth.FirebaseUser;

public class Dashboard extends AppCompatActivity implements View.OnClickListener{

    Button dashboard_btn_changepassword, dashboard_btn_logout;
    EditText dashboard_input_new_password;
    TextView dashboard_welcome;

    RelativeLayout activity_dashboard;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dashboard_btn_changepassword = (Button) findViewById(R.id.dashboard_btn_changepassword);
        dashboard_btn_logout = (Button) findViewById(R.id.dashboard_btn_logout);
        dashboard_input_new_password = (EditText) findViewById(R.id.dashboard_input_new_password);
        dashboard_welcome = (TextView) findViewById(R.id.dashboard_welcome);
        activity_dashboard = (RelativeLayout) findViewById(R.id.activity_dashboard);

        dashboard_btn_logout.setOnClickListener(this);
        dashboard_btn_changepassword.setOnClickListener(this);

        //initialize firebase
        auth = FirebaseAuth.getInstance();

        //Session Checking
        if(auth.getCurrentUser() != null){
            dashboard_welcome.setText("Welcome, " +auth.getCurrentUser().getEmail());
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.dashboard_input_new_password){
            changePassword(dashboard_input_new_password.getText().toString());

        }else if(v.getId() == R.id.dashboard_btn_logout){
            logoutUser();
        }


    }

    private void logoutUser() {
        auth.signOut();
        if(auth.getCurrentUser() == null){
            startActivity(new Intent(Dashboard.this, Login.class));
            finish();
        }

    }

    private void changePassword(String newPassword){
        FirebaseUser user = auth.getCurrentUser();
        user.updatePassword(newPassword).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Snackbar snackBar = Snackbar.make(activity_dashboard, "Password Changed Successfully", Snackbar.LENGTH_SHORT);
                    snackBar.show();
                }
            }
        });
    }
}
