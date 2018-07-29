package com.demo.valoyes.notekeeper;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Jim.
 */

public final class CourseInfo implements Parcelable {
    private final String mCourseId;
    private final String mTitle;
    private final List<ModuleInfo> mModules;

    public CourseInfo(String courseId, String title, List<ModuleInfo> modules) {
        mCourseId = courseId;
        mTitle = title;
        mModules = modules;
    }

    private CourseInfo(Parcel source){
        mCourseId = source.readString();
        mTitle = source.readString();
        mModules = new ArrayList<>();
        source.readTypedList(mModules, ModuleInfo.CREATOR);
    }

    public String getCourseId() {
        return mCourseId;
    }

    public String getTitle() {
        return mTitle;
    }

    public List<ModuleInfo> getModules() {
        return mModules;
    }

    public boolean[] getModulesCompletionStatus() {
        boolean[] status = new boolean[mModules.size()];

        for(int i=0; i < mModules.size(); i++)
            status[i] = mModules.get(i).isComplete();

        return status;
    }

    public void setModulesCompletionStatus(boolean[] status) {
        for(int i=0; i < mModules.size(); i++)
            mModules.get(i).setComplete(status[i]);
    }

    public ModuleInfo getModule(String moduleId) {
        for(ModuleInfo moduleInfo: mModules) {
            if(moduleId.equals(moduleInfo.getModuleId()))
                return moduleInfo;
        }
        return null;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseInfo that = (CourseInfo) o;

        return mCourseId.equals(that.mCourseId);

    }

    @Override
    public int hashCode() {
        return mCourseId.hashCode();
    }

    @Override
    public int describeContents() {
        // devolvemos 0 pq no tenemos ninguna necesidad de parceling especial o particular
        return 0;
    }

    @Override
    public void writeToParcel(Parcel destinationParcel, int i) {
        destinationParcel.writeString(mCourseId);
        destinationParcel.writeString(mTitle);
        destinationParcel.writeTypedList(mModules);
    }

    public final static Parcelable.Creator<CourseInfo> CREATOR =
            new Parcelable.Creator<CourseInfo>(){

                @Override
                public CourseInfo createFromParcel(Parcel sourceParcel) {
                    return new CourseInfo(sourceParcel);
                }

                @Override
                public CourseInfo[] newArray(int size) {
                    return new CourseInfo[size];
                }
            };
}
