package com.example.fodmap.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fodmap.MainActivity;
import com.example.fodmap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import static com.example.fodmap.ui.LoginActivity.EMAIL;
import static com.example.fodmap.ui.LoginActivity.PASSWD;

public class SignupActivity extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        if(savedInstanceState!=null){
            email=savedInstanceState.getString(EMAIL,"");
            passwd=savedInstanceState.getString(PASSWD,"");
        }
        emailhint=findViewById(R.id.S_emailHint);
        emailhint.setVisibility(View.INVISIBLE);
        emailinput=findViewById(R.id.S_emailInput);
        passwdhint=findViewById(R.id.S_passwdHint);
        passwdhint.setVisibility(View.INVISIBLE);
        passwdinput=findViewById(R.id.S_passwdInput);
        emailhint.setVisibility(View.INVISIBLE);
        passwdhint.setVisibility(View.INVISIBLE);
        loginbtn=findViewById(R.id.S_loginbtn);
        signupbtn=findViewById(R.id.S_signupbtn);
        upbar=findViewById(R.id.topbar);
        setSupportActionBar(upbar);
        getSupportActionBar().setTitle(R.string.action_sign_in);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseAuth=FirebaseAuth.getInstance();
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=emailinput.getText().toString();
                passwd=passwdinput.getText().toString();
                if(passwd.length()<6){
                    passwdhint.setText(new String("password length should be more than 6"));
                    return;
                }
                if(email.isEmpty()){
                    emailhint.setText(R.string.empty_input);
                    emailhint.setVisibility(View.VISIBLE);
                }
                if(passwd.isEmpty()){
                    passwdhint.setText(R.string.empty_input);
                    passwdhint.setVisibility(View.VISIBLE);
                }
                firebaseAuth.createUserWithEmailAndPassword(email,passwd).addOnCompleteListener(new SigninCompleteListener(view.getContext()));
            }
        });
    }

    class SigninCompleteListener implements OnCompleteListener<AuthResult>{
        SignupActivity context;
        SigninCompleteListener(Context activity){
            context=(SignupActivity)activity;
        }
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                context.successdo();
            }
            else {
                Exception e=task.getException();
                if(e==null){
                    return;
                }
                if(e.getClass()== FirebaseAuthInvalidCredentialsException.class){
                    emailhint.setText(R.string.invalid_email);
                }else if(e.getClass()== FirebaseAuthUserCollisionException.class){
                    emailhint.setText(R.string.collision_email);
                }else {
                    e.printStackTrace();
                }
            }
        }
    }

    void successdo(){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
