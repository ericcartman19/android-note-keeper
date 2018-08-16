package com.demo.valoyes.notekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class NoteActivity extends AppCompatActivity {
    // haremos esto en vez de poner un literal con el nombre de la clase
    private final String TAG = getClass().getSimpleName();

    // esta constante la creamos para
    // identificamos la constante de la manera mas especifica posible debido a que
    // la procedencia de informacion puede ser muy variada
    // public static final String NOTE_INFO = "com.demo.valoyes.notekeeper.NOTE_INFO";
    public static final String NOTE_POSITION = "com.demo.valoyes.notekeeper.NOTE_POSITION";
    // keys para informacion que vamos a guardar en el BUNDLE
    public static final String ORIGINAL_NOTE_COURSE_ID = "com.demo.valoyes.notekeeper.ORIGINAL_NOTE_COURSE_ID";
    public static final String ORIGINAL_NOTE_TITLE = "com.demo.valoyes.notekeeper.ORIGINAL_NOTE_TITLE";
    public static final String ORIGINAL_NOTE_TEXT = "com.demo.valoyes.notekeeper.ORIGINAL_NOTE_TEXT";

    public static final int POSITION_NOT_SET = -1;
    private NoteInfo mNote;
    private boolean mIsNewNote;
    private Spinner mSpinnerCourses;
    private EditText mTextNoteTitle;
    private EditText mTextNoteText;
    private int mNotePosition;
    private boolean mIsCancelling;
    private ArrayAdapter<CourseInfo> mAdapterCourses;
    private String mOriginalNoteCourseId;
    private String mOriginalNoteTitle;
    private String mOriginalNoteText;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // el fab lo hemos quitado, borramos este codigo

        // nos quedamos con la referencia a nuestro spinner
        mSpinnerCourses = (Spinner) findViewById(R.id.spinner_courses);

        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        // pasamos la activity actual como contexto
        // configuramos el spinner
        mAdapterCourses = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
        // ahora vamos a gestionar el drop down
        mAdapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCourses.setAdapter(mAdapterCourses);

        // procedemos a extraer la informacion del intent
        readDisplayStateValue();

        // salvamos los valores de la Activity en el momento en que se crea
        // es decir, cuando el BUNDLE es null
        // esto tambien incluye cuando una ACTIVITY ha sido destruida precedentemenete
        // y se vuelve a crear
        if(savedInstanceState == null) {
            saveOriginalNoteValues();
        }else{
            // sino es null, es decir si la ACTIVITY ya existe queremos
            restoreOriginalNoteValues(savedInstanceState);
        }

        // sacamos a las referencia a los editTextView
        mTextNoteTitle = (EditText) findViewById(R.id.text_note_title);
        mTextNoteText = (EditText) findViewById(R.id.text_note_text);

        // mostramos la informacion recuperada del intent o creamos una nueva note
        if(!mIsNewNote){
            displayNote(mSpinnerCourses, mTextNoteTitle, mTextNoteText);
        }
        Log.d(TAG, "Creating Activity....");
    }

    private void restoreOriginalNoteValues(Bundle savedInstanceState) {
        mOriginalNoteCourseId = savedInstanceState.getString(ORIGINAL_NOTE_COURSE_ID);
        mOriginalNoteTitle = savedInstanceState.getString(ORIGINAL_NOTE_TITLE);
        mOriginalNoteText = savedInstanceState.getString(ORIGINAL_NOTE_TEXT);
    }

    // permite guardar la nota tal cual estaba en el momento en que se creó la ACTIVITY
    private void saveOriginalNoteValues() {
        if(!mIsNewNote){
            // salvar todos los valores de la Note
            mOriginalNoteCourseId = mNote.getCourse().getCourseId();
            mOriginalNoteTitle = mNote.getCourse().getTitle();
            mOriginalNoteText = mNote.getText();
        }
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
    // tambien gestionaremos aqui, salvar los datod de una nueva activity
    private void readDisplayStateValue() {
        Intent intent = getIntent();
        // mNote = intent.getParcelableExtra(NOTE_POSITION);
        // hay que especificar un valor por defecto, debido a que los tipos primitivos
        // no tienen null como valor por defecto
        mNotePosition = intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET);

        // determinamos si estamos creando una nueva note o mostrando una existente
        // para esto nos basamos en la informacion que recuperamos del intent, ie, mNote
        // mIsNewNote = mNote == null;
        mIsNewNote = mNotePosition == POSITION_NOT_SET;
        if(mIsNewNote){
            // en el caso de una new note
            createNewNote();
        }

        Log.i(TAG, "mNotePosition: " + mNotePosition);
        mNote = DataManager.getInstance().getNotes().get(mNotePosition);
    }

    private void createNewNote() {
        DataManager dm = DataManager.getInstance();
        mNotePosition = dm.createNewNote();
        // nos da la position donde se ha creado la nueva note
        // mNote = dm.getNotes().get(mNotePosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    };

    // este es el metodo que es invocada cada vez que el usuario selecciona una opcion del menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send_mail) {
            sendEmail();
            return true;
        }else if(id == R.id.action_cancel){
            // flag para indicar si cancelamos la activity
            mIsCancelling = true;
            // como parte del exiting process el metodo onPause va a ser invovocado
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // realizamos la action de salvar el trabajo del usuario en onPause
        // metodo de ACTIVITY que es llamado cuando el usuario deja la ACTIVITY
        if(mIsCancelling){
            Log.i(TAG, "Cancelling note at position: " + mNotePosition);
            if(mIsNewNote){
                DataManager.getInstance().removeNote(mNotePosition);
            }else{
                // recuperamos los valores originales de la note no nueva
                storePreviousNoteValues();
            }
        }else{
            saveNote();
        }
        Log.d(TAG, "onPause");
    }

    private void storePreviousNoteValues() {
        CourseInfo course = DataManager.getInstance().getCourse(mOriginalNoteCourseId);
        mNote.setCourse(course);
        mNote.setTitle(mOriginalNoteTitle);
        mNote.setText(mOriginalNoteText);
    }

    // procedemos a guardar toda la informacion concerniente la actividad en curso del usuario
    private void saveNote() {
        // recuperamos cualquier cualquiera sea la nota que haya seleccionado el usuario
        mNote.setCourse((CourseInfo) mSpinnerCourses.getSelectedItem());
        // buscamos los valos que estan en los editText
        mNote.setTitle(mTextNoteTitle.getText().toString());
        mNote.setText(mTextNoteText.getText().toString());
    }

    private void sendEmail() {
        // titulo y contenido del mail
        CourseInfo course = (CourseInfo) mSpinnerCourses.getSelectedItem();
        String subject = mTextNoteTitle.getText().toString();
        String text = "Checkout what I learned in the Pluralsight course \"" + course.getTitle()
                + "\"\n " + mTextNoteText.getText().toString();

        // creo un Intent asociado con el envio de un mail
        Intent intent = new Intent(Intent.ACTION_SEND);
        // MIMTE TYPE for email messages, standard internet mime type
        intent.setType("message/rfc822");
        // el subject y el text sera provisto por el usuario y lo metemos en el extra
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);

        // lanzamos la activity con el Intent
        startActivity(intent);
    }

    // en este metodo nos encargamos de meter en el BUNDLE la informacion que queremos
    // persisitir en el momento que destruimos la ACTIVITY, si las llaves ya existen en el
    // BUNDLE, los valores son sobreescritos
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ORIGINAL_NOTE_COURSE_ID, mOriginalNoteCourseId);
        outState.putString(ORIGINAL_NOTE_TITLE, mOriginalNoteTitle);
        outState.putString(ORIGINAL_NOTE_TEXT, mOriginalNoteText);
    }
}
