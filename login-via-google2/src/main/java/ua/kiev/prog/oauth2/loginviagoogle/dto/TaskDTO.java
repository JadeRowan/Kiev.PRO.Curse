package ua.kiev.prog.oauth2.loginviagoogle.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TaskDTO {
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date date;

    private String text;

    public TaskDTO() {}

    private TaskDTO(Date date, String text) {
        this.date = date;
        this.text = text;
    }

    public static TaskDTO of(Date date, String text) {
        return new TaskDTO(date, text);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
