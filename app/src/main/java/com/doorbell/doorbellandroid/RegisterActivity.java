package com.doorbell.doorbellandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    EditText deviceIdText;
    EditText passwordText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        deviceIdText = findViewById(R.id.editText);
        passwordText = findViewById(R.id.editText2);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference("device/" + deviceIdText.getText().toString() + "/password");
                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get Post object and use the values to update the UI
                        String correctPassword = dataSnapshot.getValue(String.class);
                        if(correctPassword != null &&  correctPassword.equals(passwordText.getText().toString())) {
                            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Invalid ID/Password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.w("test", "loadPost:onCancelled", databaseError.toException());
                        // ...
                    }
                };
                mDatabase.addValueEventListener(postListener);

            }
        });
    }
}
