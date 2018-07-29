package com.demo.valoyes.notekeeper;

import android.content.Intent;
import android.opengl.EGLExt;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // utilizamos el FAB button para crear una nueva note
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        fab.setOnClickListener( (view) -> {
            // basicament lo unico que queremos en este caso es crear un intent
            // y llamar startActivity
            // sin embargo todo esto se puede resumir a una unica linea de codigo, like this:
            startActivity(new Intent(NoteListActivity.this, NoteActivity.class));
        });

        initializeDisplayContent();
    }

    // popula nuestro listView de una manera muy similar a la manera como el spinner se asociaba a
    // los cursos
    // luego trataremos el user-selection, es decir cuando el usuario haga click en una note
    // quereemos lanzar nuestro NoteActivity la cual va a mostrar la note en cuestion
    private void initializeDisplayContent() {
        final ListView listNotess = (ListView) findViewById(R.id.list_notes);

        List<NoteInfo> notes =  DataManager.getInstance().getNotes();
        ArrayAdapter<NoteInfo> adapterNotes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);

        listNotess.setAdapter(adapterNotes);

        // add listener
//        listNotess.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                // un intent identifica la activity que queremos crear o lanzar
//                /// tenemos que utilizar NoteListActivity.this porque estamos en una clase anonima
//                Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);
//                startActivity(intent);
//            }
//        });

        listNotess.setOnItemClickListener( (parent, view, position, id) -> {
                Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);
                NoteInfo note = (NoteInfo) listNotess.getItemAtPosition(position);
                intent.putExtra(NoteActivity.NOTE_INFO, note);
                startActivity(intent);

        });


    }

}
