package com.doorbell.doorbellandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView messageTextView;
    TextView timeTextView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageTextView = findViewById(R.id.text_message);
        timeTextView = findViewById(R.id.text_time);
        imageView = findViewById(R.id.imageView);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("door/001");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                DoorMessage value = dataSnapshot.getValue(DoorMessage.class);
                Log.d("text", "Value is: " + value);
                messageTextView.setText(value.getStatus());
                timeTextView.setText("Getting the time ...");
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = new Date(value.getTime() * 1000);
                timeTextView.setText("This alarm is received at: " + sf.format(date));
                Picasso.get().load(value.getUrl()).into(imageView);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("text", "Failed to read value.", error.toException());
            }
        });

    }
}
