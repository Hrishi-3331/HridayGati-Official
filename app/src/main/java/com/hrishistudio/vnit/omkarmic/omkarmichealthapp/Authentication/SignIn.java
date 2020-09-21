package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Home.MainActivity;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.R;

public class SignIn extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private ProgressDialog dialog;
    private CoordinatorLayout login_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = (EditText)findViewById(R.id.user_email);
        password = (EditText)findViewById(R.id.user_password);
        login_layout = (CoordinatorLayout)findViewById(R.id.login_coord);

        dialog = new ProgressDialog(SignIn.this);
        dialog.setMessage("Please wait");
        dialog.setTitle("Authenticating");
        dialog.setCanceledOnTouchOutside(false);
    }

    public void signIn(View view){
        String login_email = email.getText().toString();
        String login_password = password.getText().toString();

        if (login_email.isEmpty()){
            Snackbar.make(login_layout, "Please enter your registered email", Snackbar.LENGTH_SHORT).show();
        }
        else if (login_password.isEmpty()){
            Snackbar.make(login_layout, "Please enter your password", Snackbar.LENGTH_SHORT).show();
        }
        else {
            dialog.show();
            FirebaseAuth.getInstance().signInWithEmailAndPassword(login_email, login_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    dialog.dismiss();
                    if (task.isSuccessful()){
                        Intent intent = new Intent(SignIn.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Snackbar.make(login_layout, "Unable to authenticate. Please try again!", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
