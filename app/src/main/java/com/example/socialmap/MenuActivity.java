package com.example.socialmap;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser users = mAuth.getInstance().getCurrentUser();
    private DatabaseReference myRef;
    Button Exit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myRef = FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                DatabaseReference mDatabase;
// ...
                mDatabase = FirebaseDatabase.getInstance().getReference();
            }

        };

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(intent);
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users");
        ValueEventListener valueEventListener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user=new User();
                user = dataSnapshot.child("users").child(users.getUid()).getValue(User.class);
                TextView Name = findViewById(R.id.NameV);
                Name.setText(user.name);
                TextView Phone = findViewById(R.id.PhoneV);
                Phone.setText(user.phone);
                TextView Email = findViewById(R.id.EmailV);
                Email.setText(user.email);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //int w = Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        findViewById(R.id.Exit).setOnClickListener(this);

    }

    public void onClick(View view) {
        if(view.getId() == R.id.Exit)
        {

            mAuth.signOut();
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void Map(View v){
        Intent intent = new Intent(MenuActivity.this, MapsActivity.class);
        startActivity(intent);
    }
}
