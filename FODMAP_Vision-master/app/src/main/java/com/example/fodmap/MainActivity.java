package com.example.fodmap;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.example.fodmap.ui.IngredientsActivity;
import com.example.fodmap.ui.LoginActivity;
import com.example.fodmap.ui.SettingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    Toolbar topbar;
    FirebaseAuth firebaseAuth;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_scan:
                    return true;
                case R.id.navigation_myfood:
                    return true;
                case R.id.navigation_ingredients:
                    viewIngredients();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        topbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(topbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homemenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        MenuItem loginitem=findViewById(R.id.login_home);
        if(firebaseUser==null){
            loginitem.setTitle(R.string.login);
            loginitem.setEnabled(true);
        }else {
            loginitem.setTitle(firebaseUser.getDisplayName());
            loginitem.setEnabled(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.login_home:logindo();break;
            case R.id.setting_home: settingdo();break;
        }
        return super.onOptionsItemSelected(item);
    }

    void logindo(){
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    void viewIngredients(){
        Intent intent=new Intent(this, IngredientsActivity.class);
        startActivity(intent);
    }

    void settingdo(){
        Intent intent=new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
}
