package com.demo.valoyes.notekeeper;

import android.media.MediaPlayer;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.*;
import static android.support.test.espresso.Espresso.pressBack;

@RunWith(AndroidJUnit4.class)
public class NoteCreationTest {

    public static final String NOTE_TITLE = "Test note title";
    public static final String NOTE_TEXT = "Oye! este el el texte de la nota";
    static DataManager sDataManager;
    @BeforeClass
    public static void setup() throws Exception{
        sDataManager = DataManager.getInstance();
    }

    // vamos a necesitar una ACTIVITY para ejecutar el test
    // y para ello utilizamos esta clase ActivityTestRule
    // y a la misam se le pasa como parametro, la ACTIVITY principal del test
    // esta es la activity en la cual el test comienza
    // con la anotacion @Rule Junit sabrá que debe crear una Activity antes de cada test
    // y destruirla después de cada test
    @Rule
    public ActivityTestRule<NoteListActivity> mNoteListActivityRule
            = new ActivityTestRule<>(NoteListActivity.class);

    @Test
    public void createNewNote(){
        final CourseInfo course = sDataManager.getCourse("java_lang");

        // 1.
//        ViewInteraction fabNewNote = onView(withId(R.id.fab));
//        fabNewNote.perform(click());
        onView(withId(R.id.fab)).perform(click());

        // 1.1 buscamos el text_note en el en el spinner, esta accions es necesaria para que funcione
        // el paso 2, sino se presenta la informacion del spinner mediante un click
        // el metodo onData no va a funcionar
        // esta accions no seria necesaria, si por ejemplo estuvieramos con un ListView
        onView(withId(R.id.spinner_courses)).perform(click());

        // 2.
        // busca en los AdapterViews de esta activity y encuentra aquel cuya insancia
        // es igual a nuestra variable "course", para luego ir al spinner y encontrar la View
        // que corresponde a esta selecci'on
        // y luego haremos click en el
        onData(allOf(instanceOf(CourseInfo.class), equalTo(course))).perform(click());

        // 3.
        onView(withId(R.id.text_note_title)).perform(typeText(NOTE_TITLE));
        onView(withId(R.id.text_note_text)).perform(typeText(NOTE_TEXT), closeSoftKeyboard());

        // 4.
        pressBack();
    }

}