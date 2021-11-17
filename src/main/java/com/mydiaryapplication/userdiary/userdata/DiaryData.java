package com.mydiaryapplication.userdiary.userdata;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Date;

public class DiaryData implements Serializable {

        @NotEmpty(message = "Diary entry can't be empty")
        private String diaryNote;

        @NotNull(message = "userId can't be null")
        private String userId;

        @NotNull
        private Date onDay;

    public String getDiaryNote() {
        return diaryNote;
    }

    public void setDiaryNote(String diaryNote) {
        this.diaryNote = diaryNote;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getOnDay() {
        return onDay;
    }

    public void setOnDay(Date onDay) {
        this.onDay = onDay;
    }
}
