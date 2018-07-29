package com.demo.valoyes.notekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class NoteActivity extends AppCompatActivity {
    // esta constante la creamos para
    // identificamos la constante de la manera mas especifica posible debido a que
    // la procedencia de informacion puede ser muy variada
    public static final String NOTE_INFO = "com.demo.valoyes.notekeeper.NOTE_INFO";
    private NoteInfo mNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // el fab lo hemos quitado, borramos este codigo

        // nos quedamos con la referencia a nuestro spinner
        Spinner spinnerCourses = (Spinner) findViewById(R.id.spinner_courses);

        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        // pasamos la activity actual como contexto
        // configuramos el spinner
        ArrayAdapter<CourseInfo> adapterCourses = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
        // ahora vamos a gestionar el drop down
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourses.setAdapter(adapterCourses);

        // procedemos a extraer la informacion del intent
        readDisplayStateValue();

        // sacamos a las referencia a los editTextView
        EditText textNoteTitle = (EditText) findViewById(R.id.text_note_title);
        EditText textNoteText = (EditText) findViewById(R.id.text_note_text);

        // mostramos la informacion recuperada del intent
        displayNote(spinnerCourses, textNoteTitle, textNoteText);
    }

    // metodo encargado de mostrar la informacion recuperada del intent
    private void displayNote(Spinner spinnerCourses, EditText textNoteTitle, EditText textNoteText) {
        // recuperamos los cursos desde nueso DataManager
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        // recuperamos el index que nos interesa desde mNote (esto proviene del intent)
        int courseIndex = courses.indexOf(mNote.getCourse());
        // indicamos el index que nos interesa seleccionar en el spinner
        spinnerCourses.setSelection(courseIndex);
        // mostramos el resto de la informacion en los editTextView
        textNoteTitle.setText(mNote.getTitle());
        textNoteText.setText(mNote.getText());
    }

    // metodo encargado de extraer informacion del intent y meterlo en una variable local
    private void readDisplayStateValue() {
        Intent intent = getIntent();
        mNote = intent.getParcelableExtra(NOTE_INFO);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
