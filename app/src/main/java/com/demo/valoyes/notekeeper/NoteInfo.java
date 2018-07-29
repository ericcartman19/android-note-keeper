package com.demo.valoyes.notekeeper;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jim.
 */

public final class NoteInfo implements Parcelable {
    private CourseInfo mCourse;
    private String mTitle;
    private String mText;

    public NoteInfo(CourseInfo course, String title, String text) {
        mCourse = course;
        mTitle = title;
        mText = text;
    }

    // una de las utilidades de hacer constructores private es cuando los utilizamos para dar
    // valores a fields que son FINAL
    private NoteInfo(Parcel source) {
        // el orden es secuencial cuando leemos los valores almacenados en el parcelable object
        mCourse = source.readParcelable(CourseInfo.class.getClassLoader());
        mTitle = source.readString();
        mText = source.readString();
    }

    public CourseInfo getCourse() {
        return mCourse;
    }

    public void setCourse(CourseInfo course) {
        mCourse = course;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    private String getCompareKey() {
        return mCourse.getCourseId() + "|" + mTitle + "|" + mText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoteInfo that = (NoteInfo) o;

        return getCompareKey().equals(that.getCompareKey());
    }

    @Override
    public int hashCode() {
        return getCompareKey().hashCode();
    }

    @Override
    public String toString() {
        return getCompareKey();
    }

    @Override
    public int describeContents() {
        // debido a que no tenemos necesidades especiales de parcelling
        // debajos a 0 el return value de este metodo
        return 0;
    }

    @Override
    public void writeToParcel(Parcel destinationObject, int i) {
        // este metodo es responsble de escribir el member info
        // en el Parcelable object

        // este metodo permite meter PARCELABLE en nuestro PARCELABLE
        destinationObject.writeParcelable(mCourse, 0);

        destinationObject.writeString(mTitle);
        destinationObject.writeString(mText);
    }

    // TODO : guarrada de anoynomous class, convertirlo a lambda
    public final static Parcelable.Creator<NoteInfo> CREATOR
            = new Parcelable.Creator<NoteInfo>(){

        @Override
        public NoteInfo createFromParcel(Parcel source) {
            // permite crear una nueva instancia de nuestro tipo
            // y meter todos los valores dentro
            return new NoteInfo(source);
        }

        @Override
        public NoteInfo[] newArray(int size) {
            return new NoteInfo[size];
        }
    };
}
