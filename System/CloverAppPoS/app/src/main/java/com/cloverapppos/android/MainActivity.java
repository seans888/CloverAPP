package com.cloverapppos.android;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    @InjectView(R.id.et_username)
    EditText etUsername;
//    @InjectView(R.id.et_password)
    EditText etPassword;
//    @InjectView(R.id.bt_login)
    Button btGo;
//    @InjectView(R.id.cv)
    CardView cv;
//    @InjectView(R.id.tv_forgotPassword)
    TextView forgotPassword;
//    @InjectView(R.id.fab)
    FloatingActionButton fab;

    RelativeLayout activity_main;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        ButterKnife.inject(this);


        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        btGo = (Button) findViewById(R.id.bt_login);
        forgotPassword = (TextView) findViewById(R.id.tv_forgotPassword) ;



        // bind listeners
        fab.setOnClickListener(this);
        btGo.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);

        //Initialize Firebase
        auth = FirebaseAuth.getInstance();

//        if(auth.getCurrentUser() != null){
//            startActivity(new Intent(MainActivity.this, Account.class));
//        }


    }

    public void onClick(View view) {
        if (view.getId() == R.id.fab ) {
            getWindow().setExitTransition(null);
            getWindow().setEnterTransition(null);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options =
                        ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
            } else {
                startActivity(new Intent(this, RegisterActivity.class));

            }
        }else if (view.getId() == R.id.bt_login ) {
                Explode explode = new Explode();
                explode.setDuration(500);
                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);

//                loginUser(etUsername.getText().toString(), etPassword.getText().toString());

            startActivity(new Intent(MainActivity.this, Account.class));


        }else if(view.getId() == R.id.tv_forgotPassword){
            startActivity(new Intent(MainActivity.this, ForgotPassword.class));
            finish();
        }
    }

    private void loginUser(String email, final String password) {
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()){

                            if(password.length() < 0){

                                Snackbar snackBar = Snackbar.make(activity_main, "Password Length must be over 6", Snackbar.LENGTH_SHORT);
                                snackBar.show();

                            }
                            else{
                                startActivity(new Intent(MainActivity.this, Account.class));
                            }

                        }

                    }
                });
    }
}
