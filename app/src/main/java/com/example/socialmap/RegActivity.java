package com.example.socialmap;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser users = mAuth.getInstance().getCurrentUser();

    private EditText Email;
    private EditText Password;
    private EditText Name;
    private EditText Phone;
    private EditText Password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference();

            }

        };

        Email = (EditText) findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Password);
        Name = (EditText) findViewById(R.id.Name);
        Phone = (EditText) findViewById(R.id.Phone);
        Password2 = (EditText) findViewById(R.id.Password2);

        findViewById(R.id.Reget).setOnClickListener(this);

    }

    public void onClick(View view) {
        if (view.getId() == R.id.Reget) {
            reg(Email.getText().toString(), Password.getText().toString(), Password2.getText().toString());
        }
    }

    public void reg(String email, final String password, final String password2) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (password.length() > 5) {
                    if (password.equals(password2)) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegActivity.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                            writeNewUser("",Name.getText().toString(),Email.getText().toString(),Phone.getText().toString());
                            Intent intent = new Intent(RegActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else
                            Toast.makeText(RegActivity.this, "Данный E-mail уже зарегистрирован", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(RegActivity.this, "Пароль не совпадает", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegActivity.this, "Пароль не должен быть меньше 6 символов", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void writeNewUser(String userId, String name, String email, String phone) {
        User user = new User(name, email, phone);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.child("users").child(users.getUid()).setValue(user);
    }
}