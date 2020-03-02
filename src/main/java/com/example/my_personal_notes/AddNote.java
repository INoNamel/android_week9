package com.example.my_personal_notes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddNote extends AppCompatActivity {
    EditText edit_headline, edit_body;
    Button save;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        edit_headline = findViewById(R.id.new_headline);
        edit_body = findViewById(R.id.new_body);

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

    }

    public void saveNote() {
        String headline = edit_headline.getText().toString();
        String body = edit_body.getText().toString();

        if(!headline.isEmpty() && !body.isEmpty()) {
            DocumentReference docRef = db.collection("notes").document();
            Map<String,String> map = new HashMap<>();
            map.put("head", headline);
            map.put("body", body);
            docRef.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.i("all", "added successfully");
                }
            });
            finish();
        } else {
            Toast.makeText(this, "empty fields", Toast.LENGTH_SHORT).show();
        }
    }
}
