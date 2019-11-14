package com.example.fodmap.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.fodmap.MainActivity;
import com.example.fodmap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    EditText emailinput;
    EditText passwdinput;
    TextView emailhint;
    TextView passwdhint;
    Button loginbtn;
    Button signupbtn;
    Toolbar upbar;
    String email;
    String passwd;
    private FirebaseAuth firebaseAuth;
    public static final String EMAIL="email";
    public static final String PASSWD="password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(savedInstanceState!=null){
            email=savedInstanceState.getString(EMAIL,"");
            passwd=savedInstanceState.getString(PASSWD,"");
        }
        emailhint=findViewById(R.id.emailHint);
        passwdhint=findViewById(R.id.passwdHint);
        emailinput=findViewById(R.id.emailInput);
        passwdinput=findViewById(R.id.passwdInput);
        loginbtn=findViewById(R.id.login_L_btn);
        signupbtn=findViewById(R.id.signup_L_btn);
        emailhint.setVisibility(View.VISIBLE);
        passwdhint.setVisibility(View.VISIBLE);
        upbar=findViewById(R.id.topbar);
        firebaseAuth=FirebaseAuth.getInstance();
        setSupportActionBar(upbar);
        getSupportActionBar().setTitle(R.string.login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=emailinput.getText().toString();
                passwd=passwdinput.getText().toString();
                emailhint.setVisibility(View.INVISIBLE);
                passwdhint.setVisibility(View.INVISIBLE);
                firebaseAuth.signInWithEmailAndPassword(email,passwd).addOnCompleteListener(new LoginCompleteListener(view.getContext()));
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity context=(LoginActivity) view.getContext();
                context.signupdo();
            }
        });
    }

    class LoginCompleteListener implements OnCompleteListener<AuthResult>{
        LoginActivity context;
        LoginCompleteListener(Context activity){
            context=(LoginActivity) activity;
        }
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                successdo();
            }
            else {
                Exception e=task.getException();
                if(e==null){
                    return;
                }
                if(e.getClass()== FirebaseAuthInvalidUserException.class){
                    emailhint.setText(R.string.invalid_email);
                    emailhint.setVisibility(View.VISIBLE);
                }else if (e.getClass()== FirebaseAuthInvalidCredentialsException.class){
                    passwdhint.setText(R.string.wrong_password);
                    passwdhint.setVisibility(View.VISIBLE);
                }else {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(EMAIL,emailinput.getText().toString());
        outState.putString(PASSWD,passwdinput.getText().toString());
        super.onSaveInstanceState(outState);
    }

    void successdo(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    void signupdo(){
        Intent intent=new Intent(this,SignupActivity.class);
        startActivity(intent);
    }
}
