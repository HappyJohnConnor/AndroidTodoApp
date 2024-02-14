package com.example.todo4.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class TodoModel implements Parcelable {
    private String title;
    private String detail;

    public TodoModel(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    protected TodoModel(Parcel in) {
        title = in.readString();
        detail = in.readString();
    }

    public static final Creator<TodoModel> CREATOR = new Creator<TodoModel>() {
        @Override
        public TodoModel createFromParcel(Parcel in) {
            return new TodoModel(in);
        }

        @Override
        public TodoModel[] newArray(int size) {
            return new TodoModel[size];
        }
    };

    public String getTitle() {
        return title;
    }
    public String getDetail() {
        return detail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(detail);
    }
}
