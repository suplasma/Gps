package com.example.socialmap;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText Email;
    private EditText Password;

    Button Reg;
    Button Enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent intent = new Intent(MainActivity.this,MenuActivity.class);
                    startActivity(intent);

                }

            }
        };

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {

            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        }
        Email = (EditText) findViewById(R.id.EmailT);
        Password = (EditText) findViewById(R.id.PasswordT);

        findViewById(R.id.Enter).setOnClickListener(this);
        findViewById(R.id.Reg).setOnClickListener(this);
    }
    public void onClick(View view) {
        if(view.getId() == R.id.Reg)
        {
            Intent intent = new Intent(this,RegActivity.class);
            startActivity(intent);
        }
        if(view.getId() == R.id.Enter)
        {
            enter(Email.getText().toString(),Password.getText().toString());
        }

    }

    public void enter(String email , String password)
    {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Aвторизация успешна", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,MenuActivity.class);
                    startActivity(intent);
                }else
                    Toast.makeText(MainActivity.this, "E-mail или пароль неверный", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
