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

        // obtenemos todas las notes del DataManager, el cual es un SINGLETON
        List<NoteInfo> notes =  DataManager.getInstance().getNotes();
        // luego cargamos esta informacion en un Adapter
        ArrayAdapter<NoteInfo> adapterNotes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        // con la informacion del adapter populamos el ListView
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

        // cuando el usuario hace una selection (click) en la lista
        // entre la informacion que recibimos esta la posicion de la nota dentro de la lista
        listNotess.setOnItemClickListener( (parent, view, position, id) -> {
                Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);
                // obtebenis la nota a partir de la position que recibimos en el evento
                // NoteInfo note = (NoteInfo) listNotess.getItemAtPosition(position);
                // dicha nota la mentemos en el intent, utilizando PARCELABLE (NoteInfo lo impelmenta)
                // intent.putExtra(NoteActivity.NOTE_INFO, note);
                // debido a que las acitivies se ejecutan en el mismo PROCESS, ambas puedden
                // acceder directamente al DataManger(singleton) y no es necesario meter el
                // PARCELABLE en el intent
                intent.putExtra(NoteActivity.NOTE_POSITION, position);
                startActivity(intent);

        });


    }

}
