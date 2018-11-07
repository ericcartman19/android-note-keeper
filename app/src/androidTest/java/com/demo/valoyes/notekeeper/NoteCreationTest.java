package com.demo.valoyes.notekeeper;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class NoteCreationTest {

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
//        ViewInteraction fabNewNote = onView(withId(R.id.fab));
//        fabNewNote.perform(click());
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.text_note_title)).perform(typeText("Test note title"));
        onView(withId(R.id.text_note_text)).perform(typeText("Oye! este el el texte de la nota"), closeSoftKeyboard());
    }

}