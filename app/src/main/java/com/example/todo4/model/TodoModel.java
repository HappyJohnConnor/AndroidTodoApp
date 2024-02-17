package com.example.todo4.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

class TodoModel {
    private String id;
    private String title;
    private String detail;

    public TodoModel(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }
    public String getDetail() {
        return detail;
    }

}
