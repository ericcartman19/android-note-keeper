package com.demo.valoyes.notekeeper;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataManagerTest {

    @Test
    public void createNewNote() throws Exception{
        // usamos el throws exception para evitar de usar los try catch?
        // given
        final DataManager dataManager = DataManager.getInstance();
        final CourseInfo course = dataManager.getCourse("android_async");
        final String noteTitle = "Test note title";
        final String noteText = "This is the body of the text of my test note";

        int noteIndex = dataManager.createNewNote();
        NoteInfo newNote = dataManager.getNotes().get(noteIndex);
        newNote.setCourse(course);
        newNote.setTitle(noteTitle);
        newNote.setText(noteText);

        // when
        NoteInfo compareNote = dataManager.getNotes().get(noteIndex);

        // then
        // same compara si ambos objetos tienen la misma referencia, y no es precisamente
        // lo que queremos en este caso debido a que estamos mas interesados en
        // el texto de la nota, usaremos assertEquals en cambio
        // Assert.assertSame(newNote, compareNote);

        Assert.assertEquals(compareNote.getCourse(), course);
        Assert.assertEquals(compareNote.getTitle(), noteTitle);
        Assert.assertEquals(compareNote.getText(), noteText);

    }
}