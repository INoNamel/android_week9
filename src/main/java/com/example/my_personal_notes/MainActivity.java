package com.example.my_personal_notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.my_personal_notes.adapter.NotesAdapter;
import com.example.my_personal_notes.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private ArrayList<Note> notes;
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.notes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewNote();
            }
        });
        loadNotes();
        startNoteListener();
    }

    private void loadNotes() {
        this.notes = new ArrayList<>();
        adapter = new NotesAdapter(notes);
        recyclerView.setAdapter(adapter);
    }

    private void addNewNote() {
        Intent addNote = new Intent(MainActivity.this, AddNote.class);
        startActivity(addNote);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void startNoteListener() {
        db.collection("notes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot values, @Nullable FirebaseFirestoreException e) {
                notes.clear();
                for (DocumentSnapshot snap: values.getDocuments()) {
                    Log.i("all", "read from FB " + snap.getId() + " " + snap.get("body").toString());
                    notes.add(new Note(snap.get("head").toString(), snap.get("body").toString()));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}