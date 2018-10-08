package com.demo.valoyes.notekeeper;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NoteCreationTest {

    // vamos a necesitar una ACTIVITY para ejecutar el test
    // y para ello utilizamos esta clase ActivityTestRule
    // y a la misam se le pasa como parametro, la ACTIVITY principal del test
    ActivityTestRule<NoteListActivity> mNoteListActivityRule
            = new ActivityTestRule<>(NoteListActivity.class);

    @Test
    public void createNewNote(){
        // TODO
    }

}