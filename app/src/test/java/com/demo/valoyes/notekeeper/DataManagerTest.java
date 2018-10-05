package com.demo.valoyes.notekeeper;

import android.provider.ContactsContract;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataManagerTest {
    private static DataManager sDataManager;

    @BeforeClass
    public static void classInit() throws Exception{
        // el metodo @Before class debe ser declarado static
        // en consecuencia las variables que utilizan deben ser estaticas tambien
        sDataManager = DataManager.getInstance();
    }

    @Before
    public void setUp() throws Exception {
        // vamos a garantizar que antes de la ejecucion de cada test
        // la lista de notes estar'a limpia y reincializada (con nuevos valores)
        sDataManager.getNotes().clear();
        sDataManager.initializeExampleNotes();
    }

    @Test
    public void createNewNote() throws Exception{
        // usamos el throws exception para evitar de usar los try catch?
        // given
        final CourseInfo course = sDataManager.getCourse("android_async");
        final String noteTitle = "Test note title";
        final String noteText = "This is the body text of my test note";

        int noteIndex = sDataManager.createNewNote();
        NoteInfo newNote = sDataManager.getNotes().get(noteIndex);
        newNote.setCourse(course);
        newNote.setTitle(noteTitle);
        newNote.setText(noteText);

        // when
        NoteInfo compareNote = sDataManager.getNotes().get(noteIndex);

        // then
        // same compara si ambos objetos tienen la misma referencia, y no es precisamente
        // lo que queremos en este caso debido a que estamos mas interesados en
        // el texto de la nota, usaremos assertEquals en cambio
        // Assert.assertSame(newNote, compareNote);

        Assert.assertEquals(course, compareNote.getCourse());
        Assert.assertEquals(noteTitle, compareNote.getTitle());
        Assert.assertEquals(noteText, compareNote.getText());
    }

    @Test
    public void findSimilarNotes() throws Exception {
        final DataManager sDataManager = DataManager.getInstance();
        final CourseInfo course = sDataManager.getCourse("android_async");
        final String noteTitle = "Test note title";
        final String noteText1 = "This is the body text of my test note";
        final String noteText2  = "This is the body of my second test note";

        int noteIndex1 = sDataManager.createNewNote();
        NoteInfo newNote1 = sDataManager.getNotes().get(noteIndex1);
        newNote1.setCourse(course);
        newNote1.setTitle(noteTitle);
        newNote1.setText(noteText1);

        int noteIndex2 = sDataManager.createNewNote();
        NoteInfo newNote2 = sDataManager.getNotes().get(noteIndex2);
        newNote2.setCourse(course);
        newNote2.setTitle(noteTitle);
        newNote2.setText(noteText2);

        int foundIndex1 = sDataManager.findNote(newNote1);
        assertEquals(noteIndex1, foundIndex1);

        int foundIndex2 = sDataManager.findNote(newNote2);
        assertEquals(noteIndex2, foundIndex2);
    }

    @Test
    public void createNewNoteOneStepCreation() {
        final CourseInfo course = sDataManager.getCourse("android_async");
        final String noteTitle = "Test note title";
        final String noteText = "This is the body of my test note";

        int noteIndex = sDataManager.createNewNote(course, noteTitle, noteText);

        NoteInfo compareNote = sDataManager.getNotes().get(noteIndex);
        assertEquals(course, compareNote.getCourse());
        assertEquals(noteTitle, compareNote.getTitle());
        assertEquals(noteText, compareNote.getText());
    }
}